#pragma once
#if defined(__ANDROID__)
#include <atomic>
#include <chrono>

extern "C" void KartPadAndroidLogMetric(const char*, const char*, ...);
extern "C" long long KartPadAndroidThreadCpuNanos();

namespace kartpad::android {
// Completed host calls only: guest scheduler waits are intentionally excluded.
// A process-wide cap prevents repeated stalls from flooding the private log.
class NetworkCallTimer {
 public:
  NetworkCallTimer(const char* operation, unsigned command)
      : operation_(operation), command_(command), start_(Clock::now()),
        cpu_start_(KartPadAndroidThreadCpuNanos()) {}
  ~NetworkCallTimer() {
    const double wall_ms = std::chrono::duration<double, std::milli>(Clock::now() - start_).count();
    if (wall_ms < 100.0) return;
    unsigned remaining = budget_.load(std::memory_order_relaxed);
    do {
      if (remaining == 0) return;
    } while (!budget_.compare_exchange_weak(remaining, remaining - 1,
                                          std::memory_order_relaxed));
    const long long cpu_end = KartPadAndroidThreadCpuNanos();
    const double cpu_ms = cpu_start_ >= 0 && cpu_end >= cpu_start_
        ? double(cpu_end - cpu_start_) / 1e6 : -1.0;
    KartPadAndroidLogMetric("KartPadNetStall",
        "operation=%s command=%u wall_ms=%.3f cpu_ms=%.3f remaining=%u",
        operation_, command_, wall_ms, cpu_ms, remaining - 1);
  }
  NetworkCallTimer(const NetworkCallTimer&) = delete;
  NetworkCallTimer& operator=(const NetworkCallTimer&) = delete;
 private:
  using Clock = std::chrono::steady_clock;
  inline static std::atomic<unsigned> budget_{32};
  const char* operation_;
  unsigned command_;
  Clock::time_point start_;
  long long cpu_start_;
};
}
#endif

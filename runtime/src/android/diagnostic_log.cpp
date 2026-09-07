#include <android/log.h>
#include <cstdarg>
#include <cstdio>
#include <time.h>

// Called only for coarse runtime metrics (roughly once per 300 presents).
// stderr is already mirrored into the app's private per-launch console log.
extern "C" void KartPadAndroidLogMetric(const char* tag, const char* format, ...) {
  char message[1024];
  va_list args;
  va_start(args, format);
  std::vsnprintf(message, sizeof(message), format, args);
  va_end(args);
  __android_log_write(ANDROID_LOG_INFO, tag, message);
  timespec now{};
  clock_gettime(CLOCK_BOOTTIME, &now);
  std::fprintf(stderr, "[%s] elapsed_ms=%lld %s\n", tag,
               static_cast<long long>(now.tv_sec) * 1000 + now.tv_nsec / 1000000,
               message);
}

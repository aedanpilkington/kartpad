"""Compile and exercise completed-call diagnostics without an Android device."""
import pathlib
import shutil
import subprocess
import tempfile
import unittest

ROOT = pathlib.Path(__file__).resolve().parents[1]


class NetworkStallTest(unittest.TestCase):
    def test_threshold_and_process_log_cap(self):
        compiler = shutil.which("clang++") or shutil.which("g++")
        if not compiler:
            self.skipTest("C++ compiler unavailable")
        source = r'''
#include <kartpad/android/network_stall.h>
#include <cassert>
#include <cstdarg>
#include <cstdio>
#include <cstring>
#include <thread>
static int reports = 0;
extern "C" long long KartPadAndroidThreadCpuNanos() { return -1; }
extern "C" void KartPadAndroidLogMetric(const char* tag, const char* format, ...) {
  assert(std::strcmp(tag, "KartPadNetStall") == 0);
  char text[512];
  va_list args;
  va_start(args, format);
  std::vsnprintf(text, sizeof(text), format, args);
  va_end(args);
  assert(std::strstr(text, "operation=ssl_ioctlv command=12 "));
  assert(std::strstr(text, "cpu_ms=-1.000"));
  ++reports;
}
int main() {
  { kartpad::android::NetworkCallTimer fast("socket_ioctl", 0); }
  assert(reports == 0);
  for (int i = 0; i < 33; ++i) {
    kartpad::android::NetworkCallTimer slow("ssl_ioctlv", 12);
    std::this_thread::sleep_for(std::chrono::milliseconds(105));
  }
  assert(reports == 32);
}
'''
        with tempfile.TemporaryDirectory() as temp:
            cpp = pathlib.Path(temp) / "test.cpp"
            exe = pathlib.Path(temp) / "test"
            cpp.write_text(source)
            subprocess.run([compiler, "-std=c++17", "-D__ANDROID__", "-Wall", "-Wextra", "-Werror",
                            "-pthread", "-I", str(ROOT / "runtime/include"), str(cpp), "-o", str(exe)], check=True)
            subprocess.run([str(exe)], check=True, timeout=15)


if __name__ == "__main__":
    unittest.main()

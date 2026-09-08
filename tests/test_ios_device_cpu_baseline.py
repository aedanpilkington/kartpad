"""Device ISA selection and actual Apple compiler regression; no game build."""
from pathlib import Path
import platform
import subprocess
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
PATCH = ROOT / 'patches/wiicompiled-ios-device-cpu-baseline.patch'


class DeviceCpuBaselineTests(unittest.TestCase):
    def test_platform_selection(self):
        # Evaluate the patch's actual CMake branch. Only the target-options sink
        # is synthetic; this never configures or builds the game or a toolchain.
        postimage = '\n'.join(line[1:] for line in PATCH.read_text().splitlines()
                              if line.startswith((' ', '+')) and not line.startswith('+++'))
        branch = postimage[postimage.index('        if(CMAKE_SYSTEM_NAME'):]
        branch = branch[:branch.index('        endif()') + len('        endif()')]
        fixture = ('function(target_compile_options)\n'
                   'message(STATUS "SELECTED=${ARGV}")\nendfunction()\n' + branch)
        for system, sdk, generic in [('iOS', 'iphoneos', True),
                                    ('iOS', '/SDKs/iPhoneOS26.5.sdk', True),
                                    ('iOS', 'iphonesimulator', False),
                                    ('iOS', '/SDKs/iPhoneSimulator26.5.sdk', False),
                                    ('Darwin', 'macosx', False),
                                    ('tvOS', 'appletvos', True)]:
            with self.subTest(system=system, sdk=sdk), tempfile.TemporaryDirectory() as d:
                script = Path(d) / 'selection.cmake'
                script.write_text(fixture)
                result = subprocess.run(['cmake', '-DCMAKE_SYSTEM_NAME=' + system,
                                         '-DCMAKE_OSX_SYSROOT=' + sdk, '-P', str(script)],
                                        capture_output=True, text=True, check=True)
                self.assertIn('-mcpu=generic' if generic else '-mcpu=apple-m2', result.stdout)
                self.assertEqual('-Xclang -target-feature -Xclang -rcpc' in result.stdout, generic)

    def test_preparation_applies_after_target_patches(self):
        source = (ROOT / 'scripts/prepare-ios-game-runtime.sh').read_text()
        self.assertGreater(source.index(PATCH.name), source.index('wiicompiled-dual-product-target.patch'))
        self.assertLess(source.index(PATCH.name), source.index('if [[ "${prepare_only}" == "1" ]]'))
        self.assertNotIn(PATCH.name, (ROOT / 'scripts/prepare-android-game-runtime.sh').read_text())

    def test_device_builder_refuses_old_prepared_source(self):
        with tempfile.TemporaryDirectory() as d:
            prepared = Path(d) / 'source'
            (prepared / 'cmake').mkdir(parents=True)
            (prepared / 'CMakeLists.txt').touch()
            (prepared / 'cmake/PublicProducts.cmake').write_text('# MKW_KARTPAD_REPO_ROOT\n')
            result = subprocess.run(['bash', str(ROOT / 'scripts/build-ios-device-game-app.sh'),
                                     str(prepared), str(Path(d) / 'unused-build')],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 66)
            self.assertIn('stale iOS CPU baseline', result.stderr)

    @unittest.skipUnless(platform.system() == 'Darwin', 'requires Apple clang')
    def test_actual_acquire_instruction(self):
        source = 'unsigned char acquire(const unsigned char *p) { return __atomic_load_n(p, __ATOMIC_ACQUIRE); }\n'
        sdk = subprocess.check_output(['xcrun', '--sdk', 'iphoneos', '--show-sdk-path'], text=True).strip()
        common = ['xcrun', 'clang', '-target', 'arm64-apple-ios16.0', '-isysroot', sdk, '-O2']
        for flags, expected in [(['-mcpu=apple-m2'], 'ldaprb'),
                                (['-mcpu=generic', '-Xclang', '-target-feature', '-Xclang', '-rcpc'], 'ldarb')]:
            with tempfile.TemporaryDirectory() as d:
                obj = Path(d) / 'acquire.o'
                subprocess.run(common + flags + ['-c', '-x', 'c', '-', '-o', str(obj)],
                               input=source, capture_output=True, text=True, check=True)
                assembly = subprocess.check_output(['xcrun', 'llvm-objdump', '-d', str(obj)], text=True)
            self.assertRegex(assembly, r'\b' + expected + r'\b')
            if expected == 'ldarb':
                self.assertNotRegex(assembly, r'\bldapr\w*\b')
        rejected = subprocess.run(common + ['-mcpu=apple-a10', '-c', '-x', 'assembler', '-', '-o', '/dev/null'],
                                  input='ldaprb w8, [x8]\n', capture_output=True, text=True)
        self.assertNotEqual(rejected.returncode, 0)
        self.assertIn('instruction requires: rcpc', rejected.stderr)


if __name__ == '__main__':
    unittest.main()

# Android release candidate 63 verification

Status: reviewed and merged, publicly signed candidate packaged locally. Not
published. Physical phone remains on the owner's private local62; no phone input,
restart or install was performed during this release integration. A safe handover
request is pending. The support coordinator owns distribution and public replies.

## Exact artifacts

- Version: `0.4.14-android-preview.1`, code 63, `dev.kartpad.android`.
- Source: merged `6a2dffc30f8f0d55a7eb928c614c88e054240d14`, clean at build.
- Reviewed premerge source: `8336948b88641e75e2a3434ea173de942eb866f7`.
  The two Git trees are identical; PR #174 merged after source review and runtime
  smoke checks. No application code changed for final packaging.
- Final APK SHA-256:
  `4e27897b9bb89e7b24fbe0b2e3dc66fae4efd549edaadf2f748ca22f376d87ff`.
  Size: 110,355,865 bytes.
- Final private AAB SHA-256:
  `7d49f7dd7706b1b1f89fbaa4933f7ae4ac4a79c42ab96c567023b5646238f273`.
- Existing public certificate SHA-256:
  `c1dbe0a0d72d830a5779476b346a750d0a37515adef992cad2f3863058f7f2f2`.
- Stripped `libmain.so` SHA-256:
  `1502c10b591809d3117b1e057d2273b53ec81fe76bf87d06d31dd1114286cf42`.
- Premerge tested APK SHA-256:
  `cc0eca11878a57c1e2664f0c778492383894a09aee06fd97c3dbc21ebe2cc80e`.
  Final APK ZIP payload differs only in `assets/kartpad-build.json`; all native
  libraries, DEX, resources and game payload are identical.

The native configuration is RelWithDebInfo with `-O2 -DNDEBUG`. Disassembly of
the linked overlap policy is `mov w0, #1; ret`; its debug-property string is
absent from the APK native library. Actual emulator logs contain overlap encode
phase reports while the debug property is empty. This establishes release-path
activation, not a new matched public-build performance percentage.

## Checks and observations

- Fresh Android preparation and five extracted-source bounds/ownership/mapping/
  concurrency harnesses passed ASan/UBSan/TSan as applicable. These are not a
  sanitizer run of the complete game engine.
- 66 Android Python contracts passed. Executable Kotlin report/evidence and
  bounded-export checks passed. Retro storage regression covered 13 cases plus
  pipeline/content/space checks. Release compile and lintVital passed.
- AAB/APK content, signature and alignment audits passed unchanged. The initial
  AAB marker-count audit rejected reused stripped DiscIO input because its symbol
  metadata was absent. The original unstripped dependency restored that metadata;
  its stripped library exactly matches the existing public library. No key leak
  or runtime dependency change was established, and the audit was not weakened.
- Isolated API 36 ARM64 software-GPU emulator, 1280x720, 1x/4:3, validation off,
  audio disabled. Private owned-game fixtures were seeded directly; this is not
  an import/install-transaction test. No owner saves or identities were copied.
- Exact public28 to premerge63 update used `install -r`: all 19 protected fixture
  files identical. Final merged63 update also used `install -r`: all 25 protected
  files identical, including the newly created Retro redirected-save fixture.
  No uninstall, data clear or downgrade occurred.
- Original: 50cc, Mario, Standard Kart M, Automatic, Luigi Circuit. Intro,
  countdown, acceleration and steering worked. Home return, Report a Problem
  return and live 1440x900/1280x720 resize kept the same game process. Game time
  advanced beyond six minutes, much of it parked at a wall; no completed lap,
  race or sustained driven-performance benchmark is claimed.
- Retro: Mario, Standard Kart M, Hybrid, SNES Mario Circuit 1. Actual race intro
  says 200cc, correcting an earlier inferred 100cc label. Countdown, acceleration,
  steering and same-process Home return worked. A fresh KartPad license visibly
  reloaded after full app restart. This is not saved cup/awards acceptance.
- Captured Original console: 203 overlap reports; Retro: 133. No renderer
  error/fatal lines in those captures; crash buffer empty. Counts are diagnostic
  windows, not a frame-rate benchmark or proof of no unlogged defect.
- Real public-APK report flow blocked sharing without an evidence choice, then
  blocked an unreviewed selected text file. Review confirmation opened the share
  sheet with one harmless test file. No recipient was chosen. GitHub manual-
  attachment confirmation was inspected and canceled; no report was submitted.
  A stale inline validation message remained after review selection (cosmetic).

## Rejected inferences

Single title presses separated by long tool/model delays can overlap the attract
movie. A bounded adjacent-press test reached title, retained license and main.
Earlier stale screenshots do not establish lost native input or a controller
fix. A report-screen scroll accidentally invoked the Android Home gesture;
process/crash checks and successful return distinguish it from an app crash.
The previous local62 title trial overlapped owner gameplay and remains rejected.

The historical hashing improvement is not a new public gain. Passing Pixel
vertex probes do not resolve affected Adreno geometry. Save preservation is not
an FPS change or proof that ordinary-exit progress loss is fixed. See the
[release notes](../../releases/v0.4.14-android-preview.1.md) for scene-specific
numbers and the remaining 2.580–2.732-second menu transitions.

## Release gates and handoff

Retain this APK as the next official preview candidate. Finish companion
notices/checksums and review the documentation revision separately from compiled
source. The exact release configuration still needs physical controls/audio,
representative gameplay and restart/save acceptance after safe owner handover.
The phone uses a private signing identity: derive a matching-signer test variant
with identical payload; never uninstall to install the public certificate.

No across-device, affected-Adreno, online or completed-cup claim is supported.
The repository snapshot/build recipe is not a certification of complete
generated/dependency Corresponding Source. Preserve the existing documented
source-distribution obligations and resolve any missing required source before
distribution. Public upload and the X announcement remain separate owner
decisions after the concrete artifacts and test limits are presented.

Raw captures, logs, game fixtures, private AAB and signing material remain in
ignored local `build/release-candidate` and `build/release-final` directories.
Only sanitized measurements and artifact fingerprints belong in this record.

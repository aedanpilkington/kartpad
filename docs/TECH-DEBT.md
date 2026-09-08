# Technical debt

This file records useful engineering directions that have not fully cleared
KartPad's acceptance gates. An entry is not a release promise or evidence that a
feature works on untested hardware. External pull requests listed here are source
material only; any follow-up is maintainer-owned and its completed and remaining
gates are stated explicitly below.

## Current priorities across platforms

Reviewed 9 September 2026 against [known issues](KNOWN-ISSUES.md), the
[maintenance board](MAINTENANCE-BOARD.md) and their dated evidence. This document
records engineering gaps and what would demonstrate progress; the board owns
assignments and candidate status. The [Android investigation handoff](ANDROID-PERFORMANCE-HANDOFF.md)
contains the current ranked experiments and installed/candidate build distinctions. Open reports are not automatically one shared
bug. The tvOS experiments below remain useful, but are not the whole debt queue.

### Android stability and performance

Android is the main current stability priority. Public preview 1/code 28 adds
rating restore and diagnostics, with offline rating transfer now reporter-confirmed.
It is **not** a verified graphics, freeze or crash fix. Preview 2/code 29 is a local candidate, not a published download.

| Work / reports | Established evidence and remaining gap | Next discriminating check / acceptance |
| --- | --- | --- |
| Character/geometry corruption: [#102](https://github.com/chrissotraidis/kartpad/issues/102), [#104](https://github.com/chrissotraidis/kartpad/issues/104), [#120](https://github.com/chrissotraidis/kartpad/issues/120), [#137](https://github.com/chrissotraidis/kartpad/issues/137) | Multiple Adreno devices pass synthetic renderer probes while actual characters remain corrupted. Validation on/off has not resolved reported corruption; road textures may be a separate symptom. No verified rendering correction. | Reproduce a failing game draw and trace its transform/upload/shader inputs; verify a correction on affected hardware and a non-affected GPU. Avoid repeating already-completed synthetic or settings tests. [Draw evidence](artifacts/2026-09-09/graphics-preview28-evidence.md). |
| Online-menu stalls: [#123](https://github.com/chrissotraidis/kartpad/issues/123) | A matched build-28 log shows a 2.222-second presentation gap with thermal status 0. Recorded receive waits lie outside that gap. A separate actual-HLE probe reproduced alarm rescheduling under a recursive pump guard; its Android correction is merged, but reporter causality is unproven. | Compare the exact corrected candidate on the affected gameplay path; correlate guest progress, callbacks and presentation. Require real freeze/audio behavior to improve before calling it a fix. [Timing review](artifacts/2026-09-09/pixel-online-log-review.md), [local candidate](artifacts/2026-09-09/android-preview2-local-candidate.md). |
| Slowdown, frame pacing and heat: [#103](https://github.com/chrissotraidis/kartpad/issues/103) | Performance varies with device power mode, scene, compilation and heat. An independently reviewed scalar-context optimization remains separate from released builds; a synthetic multiply improvement is not a game FPS gain. | Matched cold/warm runs at the same resolution, track and power mode, measuring frame-time tails, audio and thermal state. Benchmark the optimization in-game before integration or performance claims. [Review boundary](artifacts/2026-09-09/maintenance-source-reviews.md), [performance guide](PERF.md). |
| End-of-cup crashes: [#128](https://github.com/chrissotraidis/kartpad/issues/128), [#131](https://github.com/chrissotraidis/kartpad/issues/131) | Reported after Next at the final race, before awards, with Original and Retro both reported affected. Shared awards/resource paths are identified; the matching termination cause is not. | Use the requested exit/console evidence to classify the failure, then exercise the affected awards transition. Do not infer a Retro-only bug or require destructive reimports. [Investigation](artifacts/2026-09-09/cup-transition-investigation.md). |
| Game-launch crash: [#143](https://github.com/chrissotraidis/kartpad/issues/143) | Honor X7D / Snapdragon 685 report after import and Launch. Exact build/profile and matching termination evidence are still needed. | Separate importer completion from native launch and identify the failing boundary before choosing a CPU/GPU correction. Do not infer incompatibility from the chipset name alone. |
| System bars and aspect: [#119](https://github.com/chrissotraidis/kartpad/issues/119), [#101](https://github.com/chrissotraidis/kartpad/issues/101) | System-bar handling has shipped changes but reporter/device confirmation remains separate. Fill Screen distortion needs its own projection/presentation reproduction. | Verify focus, chooser/game transitions and system UI on the affected device; compare the same scene in 4:3, 16:9 and Fill. Keep UI lifecycle separate from character corruption. |
| Save/rating/Mii migration: [#105](https://github.com/chrissotraidis/kartpad/issues/105) | Preview 1 implements reviewed, backed-up, matched offline rating restore; the reporter confirmed successful offline transfer on 8 September. Raw save export does not carry the Mii database, and file copying is not online rating synchronization. | Preserve that confirmed result rather than repeating the completed test request. Design Mii transfer, server-sync tests and Apple UI parity independently, preserving unrelated identities and licenses. [Support workflow](SUPPORT.md). |

### Apple and shared runtime gaps

- **Older-device launch (#135):** iPhone/iPad 0.4.13 build 29 ships the reviewed
  generic ARM64/RCpc-disabled correction. The final binary and initializer were
  audited. The reporter has now supplied original crash text for **0.4.0/build 15**;
  correlate it with that exact old binary, rather than the inspected 0.4.11.
  Do not request the same report again. Build 29 physical A10X launch remains
  pending. This is
  distinct from the tvOS A12 gate below. [Build evidence](artifacts/2026-09-09/ios-preview-build29.md).
- **External displays (#100):** source review found that surface recovery can
  replace the SDL Metal view and detach its controls. A real mirroring trigger
  is not established. Correct ownership with a forced-recovery regression,
  then test wired/wireless connect, disconnect and resume on each platform.
  [Source evidence](artifacts/2026-09-09/external-display-surface-ownership.md),
  [display plan](EXTERNAL-DISPLAYS.md).
- **Mac input and two-player rendering:** PR #112 still needs capture-cancel
  and physical-scancode corrections plus hardware acceptance. #127's character
  offsets need a same-scene main/PR comparison; controller success does not
  establish rendering correctness. [Current boundaries](KNOWN-ISSUES.md).
- **Diagnostics:** richer context/provenance is published on Android preview 1
  and iPhone/iPad build 29. Physical report export/share still needs acceptance
  on the exact Apple package. Use existing targeted logs first; bounded samples
  can miss a failing draw or an indefinitely blocked call. More logging without
  a discriminating experiment is not itself a stability fix.
- **Retro version compatibility and online behavior:** keep compiled profiles,
  installed content and service compatibility distinct. Apple pre-launch and
  Android install-time update checks are not identical. Future version rollout
  needs mismatch/recovery tests, plus exact-build race/results/reconnect checks;
  changing a pack or server field cannot add new executable compatibility.
  [Upstream updates](UPSTREAM_UPDATES.md), [online limits](ONLINE.md).
- **Long-session and peripheral acceptance:** sustained frame pacing/audio,
  motion steering, reconnect and complete three/four-player results remain
  per-platform gates. DSU (#91) and Wiimmfi (#90) are separate feature work,
  not already-supported paths. [Acceptance](PHYSICAL-ACCEPTANCE.md),
  [future features](FUTURE-FEATURES.md).

## Deferred tvOS work

The following compiler, presentation, haptics and audio work retains its own
source and physical-device acceptance boundaries.

### tvOS A12 compiler baseline

Status: defensive compiler hardening implemented; physical A12 compatibility
unverified.

The tvOS WiiCompiled targets should use a generic AArch64 CPU baseline and
explicitly disable RCpc instruction selection. `-mcpu=generic` alone is not
sufficient with the tested Apple Clang toolchain: it can still emit RCpc loads
that fault on the A12 Apple TV. Physical iOS also uses a generic/RCpc-disabled
baseline in
[0.4.13 build 29](releases/v0.4.13-ios.1.md); macOS and Simulator tuning are
separate. A10X iPad acceptance remains pending.

Completed integration evidence:

- the generated tvOS Xcode project contains `-mcpu=generic` and
  `-Xclang -target-feature -Xclang -rcpc` for every WiiCompiled runtime target;
- the final tvOS executable contains no unsupported RCpc load instructions;
- the complete unsigned tvOS build and app audit pass; and
- the complete iOS Simulator build and app audit pass without changing its CPU
  tuning.

An A12 compatibility claim still requires the exact resulting app to boot, reach
the title screen, produce audio, and complete a race on an A12 Apple TV. Until
such evidence is available, the change is described only as compiler hardening.

Source: pull request [#31](https://github.com/chrissotraidis/kartpad/pull/31)
and its physical-device follow-up. The submitted pull-request head did not
include the RCpc fix.

### tvOS settings and aspect presentation

Status: aspect-state consistency implemented; presentation choices remain deferred.

A Settings bundle could eventually expose presentation preferences, and the
aspect-ratio behavior should be made explicit. Those are separate decisions from
the first-run controller and launch-mode flow. The current controller-required
screen and mode chooser remain part of the tvOS safety and acceptance contract;
they must not disappear as a side effect of adding preferences.

The mobile settings bridge now reports the selected aspect mode through the
guest `SCGetAspectRatio` result. This removes a deterministic state mismatch
without adding new tvOS settings UI or changing the accepted launch flow.

The Issue #17 Apple TV 4K (3rd generation) report also found the fixed 1.0x
render scale visibly soft on a large display and reported that 2.0x or 2.5x
remained at 60 FPS while the device thermal state was nominal. Treat that as a
single-device optimization lead, not a new default or sustained-performance
claim. Any default change belongs with the deferred tvOS settings work and must
be benchmarked across supported Apple TV hardware and thermal states first.

Acceptance requires all of the following:

- controller-required messaging and input gating remain intact;
- preference defaults, relaunch behavior, and migration are deterministic;
- 4:3, 16:9, and fill presentation are verified in menus and gameplay without
  clipping or stretching regressions;
- the runtime's reported aspect ratio agrees with the selected presentation; and
- iPhone and iPad presentation behavior remains unchanged.

Source: pull request [#32](https://github.com/chrissotraidis/kartpad/pull/32).

### tvOS controller rumble

Status: hardware experiment required.

Core Haptics may provide controller rumble on supported tvOS controllers, but
the engine lifecycle has to be treated as recoverable state. Cached player state
must not suppress a restart after the engine resets, stops, the controller
disconnects, or the system sleeps.

Acceptance requires all of the following:

- reset and stopped handlers reconcile the engine and cached active-player state;
- unsupported controllers and haptic localities fail safely;
- start, update, stop, disconnect, reconnect, interruption, and sleep/wake paths
  are exercised;
- the emulation path remains non-blocking; and
- physical controllers confirm observable output for representative game events.

Source: pull request [#33](https://github.com/chrissotraidis/kartpad/pull/33).

### Dolby Pro Logic II to multichannel LPCM

Status: audio experiment required.

Decoded multichannel output may be useful when tvOS exposes a compatible route,
but it must preserve the existing stereo path and cannot be accepted from channel
plumbing alone. The exact app must demonstrate meaningful rear-channel content
and correct channel ordering on physical output hardware.

Acceptance requires all of the following:

- stereo remains the deterministic fallback for unsupported or changing routes;
- the physical run uses the exact submitted source and compiler baseline;
- front and rear channel mapping is verified with non-zero, audible content;
- route changes, pause/resume, underruns, latency, and a sustained soak pass; and
- documentation avoids center or LFE claims unless those channels are actually
  decoded and verified.

Source: pull request [#35](https://github.com/chrissotraidis/kartpad/pull/35).

### tvOS experiment integration order

1. Retain the statically verified A12 compiler hardening and keep physical
   compatibility explicitly unclaimed unless exact-artifact evidence arrives.
2. Treat aspect semantics as a focused change that preserves controller safety.
3. Evaluate haptics and multichannel audio as independent hardware experiments.
4. Rebase each experiment independently and resolve its runtime-host conflicts
   before combining any accepted work.

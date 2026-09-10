# Maintenance work and test board

Current queue: **10 September 2026**. Live audit: **24 open issues, two open PRs**,
including new controller request #184. Refresh GitHub before claiming work.
This is the canonical active work queue and candidate/test ledger. Historical
investigations explain evidence; they do not assign current work. See the
[coordinator runbook](MAINTENANCE-AUTOMATION.md), [workflow](MAINTENANCE.md),
[known evidence](KNOWN-ISSUES.md) and [deferred features](FUTURE-FEATURES.md).

## Published baseline

| Platform | Exact available artifact | Acceptance boundary |
| --- | --- | --- |
| Android | [0.4.14 preview 1 / code 63](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.14-android-preview.1), app source `6a2dffc`, APK SHA-256 `4e27897b9bb89e7b24fbe0b2e3dc66fae4efd549edaadf2f748ca22f376d87ff` | Published and owner-accepted payload. Includes PR #141 alarm ordering, frame overlap, initial GEN_MODE correction and PR #173 pack-save preservation. Adreno, cup, online and sustained-FPS reports remain unconfirmed. |
| iPhone/iPad | [0.4.15 / build 34](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.15-ios.1), app source `d3d300bb`, IPA SHA-256 `48d0f0fb95ee0f8f1135cbe94f01fecf4636850adb595fe7ba4bd7ef6a16e4bc` | Published reporting update; earlier chooser/CPU corrections retained. Focused UIKit/full-build/archive checks; no new full-game device or performance acceptance. |
| macOS | [0.4.15 / build 34](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.15-macos.1), app source `d3d300bb`, ZIP SHA-256 `ffaf113fa7a794824e6506e727842c08204c7f4276ccad76f33401cabffc8023` | Published Metal ownership correction. PR #112 and PR #157 excluded; tearing/external-output acceptance remains. |
| tvOS | [Experimental 0.4.11 / build 9](releases/v0.4.11-tvos.1.md) | No new candidate; current A12/gameplay acceptance separate. |

All ten September 10 downloads were audited by the manual release task; see
[exact release evidence](artifacts/2026-09-10/platform-release-verification.md).
Do not repeat completed source-delivery or package audits without a changed input.
Older public28/local 29/private-preview checkpoints remain in dated evidence and
[the prior board](https://github.com/chrissotraidis/kartpad/blob/26d1aa2c41e75b3a2f8185d0b142c589b52df304/docs/MAINTENANCE-BOARD.md).
They are not new build assignments or the current installed-device identity.

## Ready work: select by severity and evidence

Owner roles below describe the next assignment, not a claim that a worker is
running. Private execution claims live in the shared local coordinator state.
Check actual task/worktree/process state before taking over existing work.

| Priority / work | State and next owner | Next concrete output and completion condition |
| --- | --- | --- |
| P0 #169 lost Retro progress | Source map independently reviewed; reporter discriminator still needed | [Source assessment](artifacts/2026-09-10/issue169-exit-save-source.md): host operations precede queued callbacks; no deferred host-write queue found. Selector exit can interrupt a guest save chain, but no reported loss reproduced. Consume already-requested lost category/exit/same profile details before choosing a focused reproduction. PR #173 pack replacement is separate; no fix or build claimed. |
| P1 #123 online-menu freezes | Code63 test requested; coordinator awaits/ingests response | One code 63 Retro online-menu comparison using established scene/settings and offline control. Record freeze/audio/native-UI result. If negative, investigate blocked guest/wakeup boundary. No generic log request or duplicate candidate. |
| P1 #104 / Adreno graphics | Code63 test requested; source investigation remains available | One code 63 same-character selection/starting-grid result. If still broken, isolate one actual draw/transform/shader/upload invariant. Keep #102 road textures and #166 broad corruption distinct until linked by mechanism. |
| P1 #128/#131 cup/awards crash | Matching exit evidence pending; fixture reproduction may proceed | Classify Next-after-final-race termination, then exact ceremony regression. Both Original and Retro reported. Completion is ceremony/return/restart with progress, not one race or existing handler names. |
| P1 #143 post-import game launch | Targeted build/profile/exit request already pending | Consume matching evidence or reproduce the exact boundary with owned fixture. No inference from Snapdragon name; no repeat installation. |
| P2 #188 Mii import | New reproducible rejection report, Android63 / AYN Thor Android13 | 74-byte .mii from rfl_mii_extractor and WheelWizard both rejected. Compare importer validation with exporter format using synthetic fixture; [exact error requested](https://github.com/chrissotraidis/kartpad/issues/188#issuecomment-5620170161), no private database needed. Ready next allocation, no fix claimed. |
| #166 additional OnePlus 15 report | Device-only corroboration, build/profile unknown | [Version/Android/profile requested](https://github.com/chrissotraidis/kartpad/issues/166#issuecomment-5620170532). No GPU/driver or shared cause inferred; preserve separately from prior HONOR/Adreno reports. |
| P2 PR #112 Mac controls/keyboard | Current-main integration independently reviewed at `0194d5c`; full candidate build next | [Integration evidence/test card](artifacts/2026-09-10/pr112-current-integration.md): zero conflicts; focused native profiles/keyboard, assignment, settings, 200 trigger cases and three shell compilations pass. One release-version contract fails identically on baseline. Assign one owned full build; no current integrated app or physical acceptance yet. PR remains open. |
| P2 PR #157 / #127 split-screen | Draft `d6299b5`, source defect/regression established | Reconcile review and prepare an attributable candidate. Same two-player scene at 60/120 FPS plus single-player control. Seven targeted passes, one skip and two documented baseline failures are not game acceptance. |
| P2 #103/#167/#169 FPS; #135 Apple FPS | Reported performance gaps; Android and Apple attribution separate | One fixed warmed driven scene with normal power mode, frame-time tails/audio/thermals. No repeat Helio1x aspect sweep or menu-gain generalization. Follow [current Android assignment](ANDROID-PERFORMANCE-HANDOFF.md). |
| P2 #100 external output | Metal ownership correction already shipped; affected route unaccepted | Use existing Apple 34 for full-game wired connect/disconnect/resume and touch recovery. Wireless separately. Android lifecycle needs independent acceptance. |
| P2 #119/#128/#169 bars; #101 Fill | Existing bar fix lacks all-device confirmation; projection unclassified | Match actual device/display and launch/resume for bars. Separate paired scene projection/HUD comparison for Fill. |
| P2 macOS tearing | Owner-relayed, no precise reproduction | Establish build/display/refresh/window mode and distinguish tear lines from frame pacing before changing V-Sync controls. |

## Feature queue and completed portions

| Work | Current disposition / next decision |
| --- | --- |
| #184 Android right bumper → game D-pad Up | [Initial response](https://github.com/chrissotraidis/kartpad/issues/184#issuecomment-5613974997) posted10September. AYN Thor Max/Odin Controller request. Current remapping supports only gameA/B/X/Y/Z. [Bounded feature scope](FUTURE-FEATURES.md#android-d-pad-and-shoulder-remapping) defines press/release, migration, conflict/reset and controller/touch tests. No logs needed to establish the missing option. Ready for a small source proposal when stability work is hardware-blocked; no release promise. |
| #105 save/rating transfer | Offline transfer reporter-confirmed. Mii database migration and deliberate folder handoff are separate proposals, not another pending restore test. |
| #135 A10X startup | Reporter-confirmed fixed in iOS29, Original/Retro load. Remaining30–35 FPS concern stays in performance lane; no repeat crash/startup request. |
| #5 Mii/Wii Remote; #90 Wiimmfi; #91 DSU | Preserve shipped Mii/cursor pieces and outstanding peripheral acceptance. Separate enhancements remain scoped in [future features](FUTURE-FEATURES.md), behind stability and ready candidate completion. |
| #92 licensing clarity | PR #93 correction merged; awaiting upstream review. No recurring code assignment or unanswered-request reminder. |

## Test-request ledger

Each row is keyed by **symptom + exact candidate + test purpose**. A published
artifact is not a confirmed issue fix. Update requests and outcomes in place;
link the actual issue comment after posting. Do not repeat pending requests.

| Test | Candidate / recipient | State and expected response |
| --- | --- | --- |
| #123 online menu | Android 63 / existing Pixel8Pro reporter | [Requested10September](https://github.com/chrissotraidis/kartpad/issues/123#issuecomment-5613975589), response pending. Familiar online-menu sequence with previous validation-off settings and offline control; outcome pass, still fails, or unavailable. Do not repeat the request. |
| #104 character corruption | Android 63 / existing S24Ultra reporter | [Requested10September](https://github.com/chrissotraidis/kartpad/issues/104#issuecomment-5613975903), response pending. Same character/selection/grid, no repeated validation or import test. Do not repeat the request. |
| PR #112 input integration | New local Mac candidate only after integration / named Mac tester | Build not commissioned yet. Reconcile exact source and owner before claiming a candidate. |
| PR #157 viewport | New local Mac candidate / named two-player tester | Draft; separate first comparison from PR #112 to preserve attribution. |
| #100 wired output | Existing Apple 34 / affected reporter or arranged owner session | Current build available; existing device/output-chain request remains pending. No repeated nudge without new useful test context. |

Private state also tracks pending public actions so an uncertain post is checked
on GitHub before retry. Keep implementation/review/merge, artifact availability,
local/owner acceptance and reporter confirmation separate. A changed source or
candidate supersedes its old test explicitly; failure reopens the investigation,
not an automatic rebuild. Group routine documentation changes into one update.

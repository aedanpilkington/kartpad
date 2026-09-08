# Maintenance work and test board

Snapshot: 9 September 2026 (Japan time). Refresh GitHub and active work before claiming a task.
This is the tracked coordination index, not a claim that all local work is merged.
See [workflow](MAINTENANCE.md), [known issues](KNOWN-ISSUES.md) and their evidence links.
Private artifact paths and task identifiers belong in the local maintenance files.

## Ready work and dependencies

| Workstream | State / owner role | Next action and completion condition | Platform boundary |
| --- | --- | --- | --- |
| #135 iPad launch crash | Medium startup/source review; awaiting targeted system report | Determine whether chooser appears and inspect matching Exception/Termination/backtrace before attributing cause; no repeat installation | A10X/iPadOS report; declared minOS alone is not device acceptance |
| #105 rating companion restore | Medium source review cleared; PR #133 merged; Android preview published | Await the [offline real-save test](https://github.com/chrissotraidis/kartpad/issues/105#issuecomment-5586204311) requested 8 September; compare matching licenses/ratings before any online test | Android implementation; real-save acceptance pending, Apple parity not implemented |
| #123 online stalls and Pixel performance | Local investigation / owner device comparison | [Independent Medium source review cleared](artifacts/2026-09-09/maintenance-source-reviews.md) Android-only scalar multiply context reuse (a396eda); next gate is a matched game benchmark; keep separate from online-menu stalls. Owner reports 2704 physical semantic cases and host sanitizers; 66 Android contracts and fresh preparation differing only in the tested header pass; prototype multiply-only timing is not game FPS | Android; no proven freeze fix or FPS improvement percentage |
| #102/#104/#120 rendering | Ready investigation | Medium worker narrows actual failing character draw using supplied validation results and bounded draw diagnostics; record a discriminating result | Adreno evidence; do not infer same cause on Mali or macOS |
| #128/#131 cup crash, #119 bars | Await specific reporter evidence | Process exact end-of-cup transition/exit excerpt and separate display/bar lifecycle result | AYN Thor and Poco X8 Pro reports; #131 identifies Next before the awards ceremony, profile still unconfirmed |
| PR #112 controller/keyboard work | Contributor changes requested | Review changes after d18d3e6 for capture cancellation and physical-scancode mapping; run regression checks before new-head acceptance | macOS; owner/Retro acceptance pending |
| #127 two-player rendering | Await same-scene comparison | Compare main and PR source with equivalent settings before attribution | macOS Original; no demonstrated shared Android root cause |
| #100 external displays | Accepted work; source investigation can proceed | Assign one bounded scene/surface transition investigation; pair with specified device/adapter test | iPhone/iPad and Android separately; wired first, then wireless |
| Report context/provenance | PR #133 merged; Android preview released | Manual owner task builds applicable iPhone/iPad changes; record exact artifact and separate acceptance | Android and iPhone/iPad; physical Apple share tests remain |
| Other support/features | See known-issues index | Address new evidence for #103/#101/#5/#92; scope DSU #91 and Wiimmfi #90 separately | Record macOS/iOS/iPadOS/Android/tvOS applicability individually |

Use one owner per task and keep ready, active, awaiting review, awaiting device,
merged, and released distinct. New replies are not required to work on ready items.

## Build and test-request ledger

Update a row when a candidate is prepared, superseded, requested, tested or rejected.
Each actual candidate record must include source SHA, version, artifact SHA-256,
target machines, test evidence, request link/date, outcome and next action. Keep
local artifact locations in `build/maintenance/CURRENT.md`; never imply they are
public downloads. Link full provenance records instead of copying raw diagnostics.

| Candidate / work | Current disposition | Test request / next step |
| --- | --- | --- |
| Android public v0.4.12-android.2 / code 23 | Published diagnostic beta, not a verified graphics/cup-crash fix | Existing issue threads hold off/on results and pending crash/bar evidence. No repetitive requests |
| Android v0.4.13-android-preview.1 / code 28 | [Published unstable preview](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.13-android-preview.1), ARM64 Android 9+, at cecd69c504f66aa0d8a40f485406618b9b616791; APK SHA-256 `e70ff84fb64ce52294ac13608530bdbed3e22395e5fab7a8ef85799c22de0607`. PR #133 merged; physical acceptance pending | Medium rating source review cleared; 214 storage, 52 format and 66 Android contract checks reported passing; bundle-derived emulator Original rendering and 21 state files preserved. Final source, repeat signing, export/payload and public-signer emulator gates passed. PR #133 merged; anonymous downloads byte-match and downloaded APK/signature audit passes. [#105 offline rating test requested 8 September](https://github.com/chrissotraidis/kartpad/issues/105#issuecomment-5586204311); reporter outcome pending. Physical performance acceptance pending. Separate CPU context experiment excluded |
| Local macOS 0.4.12/build 27 at 271fdc1 | Historical candidate; later PR head adds keyboard behavior | Contributor hardware results apply to exact tested commits; resolve newer review findings before declaring a replacement ready |
| iPhone/iPad 0.4.13/build 29 (intended) | Separate explicitly authorized manual release task owns build, merge, publication and README; no completed candidate declared here | Manual task was interrupted and asked to resume after checking existing work. Await exact artifact/source/audit handoff; scheduled coordinator never publishes IPA |
| tvOS | No new candidate declared ready here | Track applicable shared changes and experimental device acceptance separately |

A pending request is not acceptance. When results arrive, link them and record
whether they support the fix, reject it, or require a different experiment.
Do not close a report solely because a candidate exists. Local tests, owner
acceptance, reporter confirmation and public availability are separate states.

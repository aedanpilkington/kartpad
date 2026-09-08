# Maintenance work and test board

Snapshot: 8 September 2026. Refresh GitHub and active work before claiming a task.
This is the tracked coordination index, not a claim that all local work is merged.
See [workflow](MAINTENANCE.md), [known issues](KNOWN-ISSUES.md) and their evidence links.
Private artifact paths and task identifiers belong in the local maintenance files.

## Ready work and dependencies

| Workstream | State / owner role | Next action and completion condition | Platform boundary |
| --- | --- | --- | --- |
| #105 rating companion restore | Local implementation corrected; Medium review next | Independently review correction 5652a7f, especially silent publication failures and custom NAND refusal; then integrate through PR and rebuild a corrected candidate | Android implementation; real-save acceptance pending, Apple parity not implemented |
| #123 online stalls and Pixel performance | Local investigation / owner device comparison | Separate online-menu waits from general CPU/thermal slowdown; use comparable scene/settings/temperature and existing profiling | Android; no proven freeze fix or FPS improvement percentage |
| #102/#104/#120 rendering | Ready investigation | Medium worker narrows actual failing character draw using supplied validation results and bounded draw diagnostics; record a discriminating result | Adreno evidence; do not infer same cause on Mali or macOS |
| #128 cup crash, #119 bars | Await specific reporter evidence | Process exact end-of-cup transition/exit excerpt and separate display/bar lifecycle result | AYN Thor Retro report; Original/all-device scope unconfirmed |
| PR #112 controller/keyboard work | Contributor changes requested | Review changes after d18d3e6 for capture cancellation and physical-scancode mapping; run regression checks before new-head acceptance | macOS; owner/Retro acceptance pending |
| #127 two-player rendering | Await same-scene comparison | Compare main and PR source with equivalent settings before attribution | macOS Original; no demonstrated shared Android root cause |
| #100 external displays | Accepted work; source investigation can proceed | Assign one bounded scene/surface transition investigation; pair with specified device/adapter test | iPhone/iPad and Android separately; wired first, then wireless |
| Report context/provenance | Local implementation / integration review | Review current source fingerprints and package evidence, then integrate with explicit platform limits | Android and iPhone/iPad; physical Apple share tests remain |
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
| Local Android rating/performance/provenance candidates | Integration review; older packages do not contain 5652a7f safety correction | Do not offer rating restore from older packages. Medium review and corrected-source build precede named owner tests; preserve existing Pixel comparison |
| Local macOS 0.4.12/build 27 at 271fdc1 | Historical candidate; later PR head adds keyboard behavior | Contributor hardware results apply to exact tested commits; resolve newer review findings before declaring a replacement ready |
| iPhone/iPad test IPA | No new candidate declared ready here | Build/audit locally when a concrete change warrants device testing; ask owner with precise steps. Scheduled job never publishes IPA |
| tvOS | No new candidate declared ready here | Track applicable shared changes and experimental device acceptance separately |

A pending request is not acceptance. When results arrive, link them and record
whether they support the fix, reject it, or require a different experiment.
Do not close a report solely because a candidate exists. Local tests, owner
acceptance, reporter confirmation and public availability are separate states.

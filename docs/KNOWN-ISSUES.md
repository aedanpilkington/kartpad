# KartPad known issues

## Community reports reviewed 8 September 2026

See the [support guide](SUPPORT.md) for workarounds and the exact diagnostic
steps. Open reports are not treated as verified root causes.

| Issue | Current boundary / next evidence |
| --- | --- |
| [#105](https://github.com/chrissotraidis/kartpad/issues/105) save location/transfer | The [Android 0.4.12-android.1 testing APK](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.12-android.1) adds profile-aware raw-save transfer. Reporter restored the license but saw a missing Mii and online rating 5000. The picker omits Retro's separate `RRRating.pul` and the Mii database; login also synchronizes ratings. Reporter confirmed WiiCompiled 0.2.31, Retro 6.12.7, an existing companion file in the shared Dolphin NAND, matching friend codes and a completed race without rating synchronization. Complete migration is not accepted; companion transfer needs backups and profile matching. |
| [#102](https://github.com/chrissotraidis/kartpad/issues/102), [#104](https://github.com/chrissotraidis/kartpad/issues/104) Android geometry/textures | Fold #102 supplied Vulkan/Adreno 840 driver 512.842.19 logs confirming 1x/4:3, both screen states, Retro Rewind 6.12.7. A [synthetic renderer probe](../tools/renderer-probe/README.md) tests packed decoding and uniform layouts; Mac Metal and physical Pixel Vulkan baselines pass. Adreno 840 (512.842.19) and Adreno 750 (512.762.39 / 512.762.41) all pass the compute checks; the reporter confirms matching Original corruption and no logged GPU errors. The expanded [0.2.0 diagnostic](https://github.com/chrissotraidis/kartpad/releases/tag/renderer-probe-v0.2.0) now tests synthetic indexed draws, textures and queued updates; the #104 reporter also passes all eight compute/draw checks on Adreno 750 / 512.762.41. Adreno 840 draw results remain open. #104 confirmed corruption at 1x/4:3 affecting drivers only, with vehicles/tracks correct; no more repeat logs are requested from that reporter. Character transforms and generated shaders need a failing-draw reproduction; #102’s road-texture symptom remains separate. No verified renderer correction. |
| [#103](https://github.com/chrissotraidis/kartpad/issues/103) Android frame drops | Reporter improved behavior after changing Game Booster+ mode and resolution. Same-mode, same-scene resolution comparison and phase/thermal excerpts requested. |
| [#101](https://github.com/chrissotraidis/kartpad/issues/101) Fill Screen distortion | 16:9/4:3 fallback; paired scene screenshots and technical report requested. Shared projection correction needs reproduction and cross-platform checks. |
| [#100](https://github.com/chrissotraidis/kartpad/issues/100) external video black with audio | Device/output chain, local-screen behavior and connection-order comparison requested. Mirroring and a dedicated external game view need separate acceptance. |
| [#94](https://github.com/chrissotraidis/kartpad/issues/94) console serial | Client fix is shared; tvOS 0.4.11 rebuild completes the distribution work. Historical server CSNums/bans require service-admin review. |
| [#91](https://github.com/chrissotraidis/kartpad/issues/91) DSU phone controller | Open experimental feature; tester identified Apple TV 4K 3rd gen and DSUController 3.0.8. Exact tvOS and phone layout requested. No DSU build yet. |
| [#90](https://github.com/chrissotraidis/kartpad/issues/90) Wiimmfi | Open compatibility feature, no release commitment. Requires matching executable integration and private identity/authentication handling, not just a MAC/host field. |
| [#5](https://github.com/chrissotraidis/kartpad/issues/5) Mii/Wii Remote | Shipped cursor/Mii work; reporter directed to current ready-made Mac download for remaining experimental Wii Remote/Nunchuk hardware results. |
| [#92](https://github.com/chrissotraidis/kartpad/issues/92) licensing clarity | Correction already merged in #93; remains open for upstream author's review. No new unanswered follow-up at review time. |

Latest renderer evidence: #104's video shows character corruption in both
vehicle selection and the starting grid, before driving. #102's second device,
Galaxy Tab S7 FE / Adreno 619, also passes all eight synthetic checks. Its
Android 14 / 0.4.10 build logs confirm Retro at 1× / Fill Screen. The latest
session ends after roughly 9 FPS with no captured termination reason; the sole
fatal report is an earlier missing-DVD-root startup, followed by successful
game loads. The tablet reporter subsequently completed a race at 4:3 after
also moving the app from microSD to internal storage. Both changed, so neither
is established as the cause; keep that working setup. These are separate
observations, not one established GPU bug.

[#120](https://github.com/chrissotraidis/kartpad/issues/120) adds a OnePlus
OPD2514 / Android 16 / build 21 / Retro 6.12.7 report with large stretched
surfaces obscuring the race. Reinstalling and clearing caches did not help.
The exact GPU/driver is not inferred from the device name.

The [Android 0.4.12-android.2 diagnostic beta](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.12-android.2)
adds opt-in validation/bounds protection for the actual game renderer and
bounded OS exit history. It also fixes the Android system bars remaining visible
during play ([#119](https://github.com/chrissotraidis/kartpad/issues/119)); local
release-emulator checks cover launch, menu return, Home/resume and transient
edge swipes. Reporter hardware acceptance is pending. Normal mode keeps the
previous renderer toggles.
Local host/emulator checks pass; affected Adreno testing and a failing game
draw remain open. This is an Android diagnostic, not a macOS/iPadOS fix.

## macOS contribution under local review

[#112](https://github.com/chrissotraidis/kartpad/pull/112) proposes controller
assignment/profiles, trigger bindings, native settings and menu integration.
The revised contributor head `271fdc1` includes the trigger correction and
a narrower controller layout. The full dual runtime rebuilds and audits, the
200-case trigger/profile checks pass, and the right-hand controls fit in the
actual native window. A local Apple Silicon candidate (0.4.12/build 27) is
available. It is **not approved**: physical controller/race acceptance and
portable contributor documentation remain open. See the [local review record](artifacts/2026-09-08/macos-pr112-review.md)
for exact source and acceptance limits.

Next work prioritizes completing this bounded controller fix and verifying
save-migration companion data. Android character rendering needs an actual
failing draw reproduction; synthetic passes are insufficient. Performance and
external-display reports still need their requested comparisons. DSU and
Wiimmfi remain independent feature work, not bugs blocked solely on logs.

Profile-aware save management, reusable network-controller input, and any
shared projection/service fixes need platform-specific UI and acceptance work.
An Android picker or a tvOS prototype does not establish parity across hosts.

## KI-001 — Limited free build storage

- Severity: project infrastructure risk (not yet a product defect)
- Observed: approximately 21 GiB free on 2026-08-28 before upstream dependency checkout and translation.
- Impact: full dependency graphs, generated translated shards, and parallel platform builds may exhaust disk space.
- Mitigation: measure each fetch/build stage, keep generated data ignored, use bounded builds, and do not delete user data without explicit authorization.

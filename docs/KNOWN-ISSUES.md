# KartPad known issues

## Community reports reviewed 8 September 2026

See the [support guide](SUPPORT.md) for workarounds and the exact diagnostic
steps. Open reports are not treated as verified root causes.

| Issue | Current boundary / next evidence |
| --- | --- |
| [#105](https://github.com/chrissotraidis/kartpad/issues/105) save location/transfer | Reporter confirmed PC WiiCompiled → Android KartPad, Retro Rewind/PAL on both. Profile-aware import/export is implemented in source for all three profiles, with isolated backups and recreation-safe picker targeting. Host fault/recovery tests and Android synthetic storage tests pass. [Android 0.4.12-android.1 testing APK](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.12-android.1) contains the fix; reporter transfer acceptance remains open. No further logs needed. |
| [#102](https://github.com/chrissotraidis/kartpad/issues/102), [#104](https://github.com/chrissotraidis/kartpad/issues/104) Android geometry/textures | Fold #102 supplied Vulkan/Adreno 840 driver 512.842.19 logs confirming 1x/4:3, both screen states, Retro Rewind 6.12.7. A [synthetic renderer probe](../tools/renderer-probe/README.md) tests packed decoding and uniform layouts; Mac Metal and physical Pixel Vulkan baselines pass. Adreno 840 (512.842.19) and Adreno 750 (512.762.39 / 512.762.41) all pass the compute checks; the reporter confirms matching Original corruption and no logged GPU errors. The expanded [0.2.0 diagnostic](https://github.com/chrissotraidis/kartpad/releases/tag/renderer-probe-v0.2.0) now tests synthetic indexed draws, textures and queued updates; the #104 reporter also passes all eight compute/draw checks on Adreno 750 / 512.762.41. Adreno 840 draw results and #104’s 1x/4:3 gameplay comparison remain open. No verified renderer correction. |
| [#103](https://github.com/chrissotraidis/kartpad/issues/103) Android frame drops | Reporter improved behavior after changing Game Booster+ mode and resolution. Same-mode, same-scene resolution comparison and phase/thermal excerpts requested. |
| [#101](https://github.com/chrissotraidis/kartpad/issues/101) Fill Screen distortion | 16:9/4:3 fallback; paired scene screenshots and technical report requested. Shared projection correction needs reproduction and cross-platform checks. |
| [#100](https://github.com/chrissotraidis/kartpad/issues/100) external video black with audio | Device/output chain, local-screen behavior and connection-order comparison requested. Mirroring and a dedicated external game view need separate acceptance. |
| [#94](https://github.com/chrissotraidis/kartpad/issues/94) console serial | Client fix is shared; tvOS 0.4.11 rebuild completes the distribution work. Historical server CSNums/bans require service-admin review. |
| [#91](https://github.com/chrissotraidis/kartpad/issues/91) DSU phone controller | Open experimental feature; tester identified Apple TV 4K 3rd gen and DSUController 3.0.8. Exact tvOS and phone layout requested. No DSU build yet. |
| [#90](https://github.com/chrissotraidis/kartpad/issues/90) Wiimmfi | Open compatibility feature, no release commitment. Requires matching executable integration and private identity/authentication handling, not just a MAC/host field. |
| [#5](https://github.com/chrissotraidis/kartpad/issues/5) Mii/Wii Remote | Shipped cursor/Mii work; reporter directed to current ready-made Mac download for remaining experimental Wii Remote/Nunchuk hardware results. |
| [#92](https://github.com/chrissotraidis/kartpad/issues/92) licensing clarity | Correction already merged in #93; remains open for upstream author's review. No new unanswered follow-up at review time. |

Profile-aware save management, reusable network-controller input, and any
shared projection/service fixes need platform-specific UI and acceptance work.
An Android picker or a tvOS prototype does not establish parity across hosts.

## KI-001 — Limited free build storage

- Severity: project infrastructure risk (not yet a product defect)
- Observed: approximately 21 GiB free on 2026-08-28 before upstream dependency checkout and translation.
- Impact: full dependency graphs, generated translated shards, and parallel platform builds may exhaust disk space.
- Mitigation: measure each fetch/build stage, keep generated data ignored, use bounded builds, and do not delete user data without explicit authorization.

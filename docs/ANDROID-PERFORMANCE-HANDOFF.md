# Android investigation handoff

Current starting point: **published Android 0.4.14 preview 1/code 63**. Read the
[maintenance board](MAINTENANCE-BOARD.md) for priorities and candidate/test state,
then [the coordinator runbook](MAINTENANCE-AUTOMATION.md) for claims and dispatch.
The [September9 assignment](archive/android-performance-handoff-2026-09-09.md)
is historical. Do not rebuild its preview 2 or assume the phone still has code 25.

## Start from available evidence

The [September10 release record](artifacts/2026-09-10/platform-release-verification.md)
pins application `6a2dffc`, APK hash, signer compatibility and owner acceptance.
PR #141 alarm ordering, frame overlap, first GEN_MODE state correction and PR #173
pack replacement save preservation are already included. The release does not
resolve every reported freeze, Adreno draw, cup crash or performance gap.

Refresh all issue bodies/comments, including unlabeled reports, PR heads and
actual owner/process state. One coordinator owns public replies. One worker
owns the Android native build/device session. Source-only work can proceed in
isolation; a historical owner label or a scheduled wake never authorizes taking
over a phone in use. Preserve private inputs, every save/profile/identity and
signing. Never uninstall, clear data, or force a signer migration.

## Next tasks

1. **Existing candidate comparisons:** code 63 to #123's familiar online menu,
   and #104's same character selection/starting grid. Verify relevant patch
   inclusion, candidate identity and any newer reply first. Do not re-request
   completed validation, import, settings or broad logs. Record a negative
   comparison as a result before deciding another experiment.
2. **Data loss/transition:** #169 ordinary-exit loss remains separate from PR #173.
   Use safe fixture/source evidence while its requested lost-data/exit details
   are pending. #128/#131 need matching exit classification or an owned ceremony
   reproduction; Original and Retro share a reported transition, not proven cause.
3. **Actual draw:** if affected Adreno still fails, select one observed character
   draw and a specific transform/shader/upload invariant. Passing Pixel and
   generic compute/draw probes cannot accept Adreno gameplay. Keep character-only,
   road-texture and broad HONOR corruption distinct until a mechanism links them.
4. **Performance:** one current-build warmed driven scene at stable settings,
   normal power mode and comparable thermal state. Measure frame-time tails,
   audio and exact scene as well as FPS. Existing menu on/off gains do not
   establish a public28-versus63 speedup or sustained racing improvement.

Each assignment needs an expected distinguishing observation, exact source,
output, tester/fixture and completion condition. After two non-informative
attempts obtain independent review and choose a different experiment or precise
dependency. Do not rotate through previously passing probes or old recordings.

## Candidate and device gate

Build only for a reviewed correction or a narrowly justified diagnostic that
an identified tester/fixture can exercise. Use the intended non-debug configuration
for a performance candidate; APK build defaults alone are not sufficient.
Record exact version/code, source, native payload, SHA-256 and signer compatibility.
Use current candidate when it already answers the question.

Host/ART regression, emulator update preservation, package audits and physical
acceptance remain separate. Physical acceptance names the operation: selection,
controls, driven race, cup/ceremony, return/restart with saves, or online results.
Never convert one completed milestone into universal hardware support. A new
artifact produces a concrete short test card, not a broad request to test everything.

## References

- [Alarm evidence](artifacts/2026-09-09/issue-123-alarm-reschedule-guard.md) and
  [online timing](artifacts/2026-09-09/pixel-online-log-review.md).
- [Actual-draw limits](artifacts/2026-09-09/graphics-preview28-evidence.md),
  [cup-transition investigation](artifacts/2026-09-09/cup-transition-investigation.md).
- [Current release63 record](artifacts/2026-09-10/android-release63-verification.md),
  [source delivery](artifacts/2026-09-10/android-source-delivery.md).
- [Build](../android/README.md), [install](INSTALL_ANDROID.md),
  [physical procedures](ANDROID-PHYSICAL-HANDOFF.md), [performance](PERF.md).

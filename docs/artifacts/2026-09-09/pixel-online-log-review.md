# Pixel online log review

[#123 reporter excerpt](https://github.com/chrissotraidis/kartpad/issues/123#issuecomment-5588528774)
was reviewed by Astra Medium against published cecd69c diagnostic semantics.
Reporter says 0.4.13; the excerpt itself omits exact build/provenance/settings,
so this session is not independently identified as build28. Earlier confirmed
Pixel/profile/settings remain historical context.

| elapsed_ms | FPS window | CPU interval | Queued pipelines |
| --- | --- | --- | --- |
| 11931421 | 48.74 FPS,50 samples,p95 26.20ms,worst156.83ms | 46.7% over6.831s | 43 |
| 11939444 | 37.90 FPS,41 samples,p95 59.69ms,worst149.94ms | 70.8% over8.022s | 0 |

Presentation statistics cover timestamped intervals in the last second,
sampled after another300presentations. CPU is calling-thread time/wall time in
a separate interval, not measured network latency. Phase windows also differ;
cpu_ms=-1 means unavailable, not GPU time. Zero queued pipelines at the second
snapshot does not exclude earlier compilation or all graphics waits.

Scheduler recovery warnings are present but do not establish a repeated busy
loop or the reported multi-second freeze. Failed-park warning frequency is
limited per guest thread. No definite defect or root cause is established.

No NetWait/NetStall or health samples appear in this selected paste. Network tags
are emitted through the same console/stderr mirror as performance metrics,
not into android-health.log. Completed >=100ms and active >=1s timers each have
32-record budgets; active tracking holds8calls and samples about once a second.
Guest scheduler waits are outside them. Absence from a selected excerpt cannot
clear the network path.

[Requested existing-file excerpts](https://github.com/chrissotraidis/kartpad/issues/123#issuecomment-5589280002):
same-session console network tags and health samples around elapsed_ms11920000
through11950000. No new run or full archive requested. An overlapping timed
host call with wall time far exceeding CPU would guide network investigation;
otherwise confirmed context plus scheduler/producer evidence guides the next
bounded experiment. No code change, build or device operation in this review.

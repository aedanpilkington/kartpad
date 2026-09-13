# Android controls and device acceptance — 13 September 2026

The owner accepts code78 after a playable random Retro single-player race and
reports touch settings working. The owner sees two online licenses and explicitly
accepts leaving them intact. No license deletion, merge or identity reset is
justified. Retro WFC menus may feel slower than before, but this is uncertain,
not a measured regression or online race/results/reconnect acceptance.

| Request | Actual state and next action |
| --- | --- |
| #184 shoulder to D-pad Up | PR219 merged; code78 includes it, public code65 does not. General race acceptance is not a physical remapping test. |
| #238 Show D-pad control | Latest reporter sees dim D-pad but only Back, no Show. Editor has fixed800dp children plus padding. Responsive editor correction and small-landscape verification are next; instructions alone are not a solution. |
| FPS size | New owner request: existing Show FPS boolean is insufficient. Add persistent size submenu without changing render resolution or touch scale. |
| #119 notification/navigation bars | Correction shipped since build23, reporter acceptance pending. Verify launch/menu/resume; do not advertise as newly implemented. |
| #202 Thor fullscreen | Surface/inset boundary remains unresolved, distinct from transient bars. No projection change without reproduction. |
| #197 ipega menu input | Touch/controller failure after controller use remains open. Needs touch-only to controller-use/disconnect to touch handoff test. |
| #5 and #91 | Mainly Mac Wii controllers and tvOS DSU; separate from Android touch editor scope. |
| #162 motion steering | Reporter closed after finding existing setting. No new feature needed. |

## Signing correction

The established community release identity was located and its certificate
matches the published Android signer. Earlier missing-key reports were wrong:
the search omitted PKCS12 files. Credential contents and paths remain private.
Public APK derivation can proceed once the final candidate is accepted and its
clean release provenance, signer, archive and notices checks pass.

The code78 pre/post inventory checked identical paths, not byte hashes.
Preserve that distinction in any release notes. Two observed licenses alone do
not establish a new identity created by this upgrade.

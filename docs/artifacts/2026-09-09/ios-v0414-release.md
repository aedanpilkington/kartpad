# iPhone/iPad 0.4.14 build 33 release evidence

Manual owner release authorized after physical acceptance of build 32 on
9 September 2026. PRs #156, #159 and #161 are merged. Build 33 retains the
accepted app changes and updates iOS release metadata to 0.4.14/build 33.
The owner explicitly keeps Android's performance work independent.

## Acceptance and scope

The owner accepted controller gameplay, menus and the final chooser candidate
on the M2 iPad. The build-32 in-place update preserved all 32 protected files,
19,871,308 bytes, byte-for-byte. This is not touch-gameplay, custom-remapping,
external-display or broad-device acceptance. See the [chooser audit](mobile-game-chooser-audit.md)
and [Metal view reproduction](apple-metal-surface-recovery.md).

The compact iPhone chooser has zero vertical overflow in six exact-source
UIKit fixture cases: missing-data, ready and paused on iPhone 17 Pro and SE 3.
Help, Done, selection callbacks and both GitHub destinations were exercised.
The pinned SunPad overlay snapshot is unchanged. Pre-release source validation
passes 153 Python tests with one skip; final version/package results follow.

## Cross-platform handoff

- The duplicate Render row removal is already on main for Android as isolated
  commit `fc6ee29`. Its settings persistence/visual contracts and Kotlin compile
  passed during PR #159; Android device acceptance belongs to its build owner.
- The Metal ownership fix applies to Apple preparation paths; it is not an
  Android Vulkan optimization. macOS and tvOS binaries are not republished here.
- The new chooser/help remains Apple-only pending Android adaptation by its
  owner. Android's reviewed-log acknowledgement/inability path is also a
  distinct reporting follow-up; build 33 provides attachment guidance and does
  not claim complete parity with that newer Android flow.
- PR #157 viewport interpolation and Android CPU/frame-pacing experiments stay
  separate until their own acceptance. No unmeasured optimization is imported
  into this owner-accepted Apple release.

## Publication evidence

Exact merged source, public IPA SHA-256, size, repeat packaging and anonymous
hosted-download verification will be recorded here by the manual release owner.
No private device data, identifiers, screenshots or signing material are part
of the public artifact.

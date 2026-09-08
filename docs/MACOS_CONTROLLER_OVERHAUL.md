# Local macOS controller improvements

Source baseline: KartPad `v0.4.11-macos.1`, commit `0c700618e91eda0eac86cd134e3130c8f4fc37a0`.
Working branch: `macos-controller-overhaul`.

## Run the current build

Quit the older KartPad after finishing your current game, then run:

```sh
open "/Users/aedanpilkington/KartPad Home/build/KartPad-multibind.app"
```

This is a **base Mario Kart Wii build**. Your downloaded dual-game app remains untouched; Retro Rewind was not rebuilt or play-tested. Existing game data, Config.toml and saves are reused. No disc contents were modified. The existing DOL and REL matched the pinned RMCP01 revision-0 hashes.

Open **Controls → Controller Settings** (also accessible from KartPad Settings).

To use **A or RT for Accelerate / Select**:

1. Keep A in the first binding slot.
2. Click **+ Add** beside it.
3. Release buttons and triggers, then fully pull RT.
4. Click **Save Profile**.

Either input activates the same action in menus and races; this does not switch mappings based on game context. Each action supports two alternative inputs, not a simultaneous-button chord. LT and RT use the configured trigger threshold. If a control is shared with another action, remapping succeeds and shows a warning. Clear removes both bindings for the action. Clearing Item or Drift restores its underlying analogue trigger behavior. Bind Drift to RB if RT should accelerate without also drifting.

## Findings and changes

- macOS GameController and the pinned SDL 3.4.4 both detected the connected Xbox One controller and SDL opened it successfully.
- The old native Controller Settings menu only synthesized F10 to toggle the toolbar. It now opens a dedicated AppKit controller panel.
- Newly discovered controllers were explicitly left unassigned. The first controller now takes Player 1 only when no saved Player 1 preference exists.
- Assignment and unassignment updated SDL without updating Aurora's cached player index. Both now change together, including when displacing another device.
- The raw joystick wizard emitted `platform:Windows` on macOS. It now uses SDL's actual platform name.
- SDL suppresses controller events when an AppKit panel owns focus instead of an SDL window. The panel scopes the background-input hint to its active lifetime and restores the prior value on close.
- The user initially still received no input, then confirmed input worked after unplugging/reconnecting. **The cold-connect issue is not conclusively resolved**; the focus change alone was insufficient in that test.
- The initial remapper listened only to buttons, so analogue triggers could never bind. Trigger capture and runtime translation now use explicit LT/RT binding identifiers and the real trigger thresholds.
- The initial duplicate-binding restriction made most already-mapped buttons unavailable. Shared bindings now work with a visible warning.
- Primary and alternative bindings are independently editable and persisted per hashed GUID/serial identity. Existing legacy primary/secondary bindings and dead zones are retained when first saving a profile. Identical devices without serials share a profile.
- Profiles are atomically saved in `~/Library/Application Support/KartPad/ControllerProfiles.json`. Invalid JSON is preserved rather than overwritten.
- The existing diagnostics report includes controller subsystem state, detected devices, assignments and profile status. Raw serials and profile keys are not included.
- The package audit now accepts its actual build product instead of requiring dual-game-only selectors for a supported base build.

## Relevant files

- `apple/macos/KartPadControllers.inc.mm`: native panel, capture, profile persistence, live tester and diagnostics.
- `apple/macos/KartPadMacShell.mm`: menu and Settings integration.
- `patches/aurora-macos-controller-assignment.patch`: default assignment and cached-index corrections.
- `patches/aurora-macos-trigger-bindings.patch`: shared binding predicate and trigger-to-game-input handling.
- `patches/wiicompiled-macos-controller-settings.patch`: runtime panel integration and wizard platform correction.
- `scripts/prepare-g7-game-runtime.sh`: applies the patches to disposable runtime sources.
- `tests/macos/controller_profiles.mm` and the two controller test scripts: profile, binding and assignment regression coverage.

## Validation

- Full ARM64 base-game runtime compiled, linked, packaged and passed the product-aware macOS package audit and strict codesign verification.
- Earlier native-panel build launched with existing game data; the panel showed the physical Xbox as Player 1 and retained the user's existing bindings.
- User confirmed input after reconnecting; subsequent game rendering reached race results.
- Automated tests cover profile round trips, legacy secondary bindings, dead zones, malformed-profile preservation, shared bindings, clearing, and Xbox labels.
- Real SDL virtual input verifies that A and RT independently activate the binding predicate used by the runtime, and release deactivates it. The A/RT pair survives save/reload.
- Tests exercise the prepared runtime's actual player-assignment functions for cached-index fallback, displacement, unassignment and Player 4.
- Applying both Aurora patches to clean pinned sources reproduces the compiled input sources exactly.
- The newest multi-binding build has not yet been physically tested in a race. The running older game was deliberately left open at race results.

## Rebuild this prepared workspace

The existing translation and dependency build are available locally. `build/generated` points at the validated private base translation.

```sh
cd "/Users/aedanpilkington/KartPad Home"
cmake --build build/self-build-macos-build --target WiiCompiled --parallel 4
scripts/test-macos-controller-profiles.sh
python3 scripts/test-macos-controller-assignment.py
scripts/package-macos-runtime.sh \
  "$PWD/build/self-build-macos-build" "$PWD/build/KartPad-next.app"
scripts/audit-macos-package.sh "$PWD/build/KartPad-next.app" base
open "$PWD/build/KartPad-next.app"
```

Choose a fresh app output name if `KartPad-next.app` already exists. Native shell source changes are picked up directly. If changing patch files, regenerate the prepared runtime with `scripts/build-macos-app.sh` using fresh source/build/output paths; merely editing a patch does not update an already prepared runtime.

## Manual checklist

- Launch with Xbox already connected; verify button and axis feedback. If input is absent, reconnect and record that it was necessary.
- Assign to Player 2, back to Player 1, then unassign/reassign; verify the displayed assignment and game behavior.
- Add RT as the alternative to Accelerate / Select's A. Verify both work independently and releasing either clears that input.
- Save, quit and reopen; verify A + RT remain configured.
- Test Cancel, Clear, shared bindings and trigger threshold changes.
- Verify steering, brakes, drift, item, pause, D-pad and keyboard in a race.
- Disconnect/reconnect while the controller panel is open and during gameplay.

Remaining scope: a broader first-run wizard, game-context-dependent mappings, more than two bindings per action, raw-unmapped-device native remapping, and a rebuilt/validated dual-game Retro Rewind package are not included.

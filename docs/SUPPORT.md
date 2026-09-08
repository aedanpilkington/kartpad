# KartPad support

Use the [platform downloads](../README.md#platform-overview) and include the
exact app version/build in a report. Update over the existing installation;
do not uninstall or clear storage to troubleshoot. Follow the platform's
backup instructions before changing saves or signing identities.

## Android save transfer

KartPad stores saves in Android's **internal app-private storage**, so its save
folder is not exposed through a normal file manager under `Android/data`.
The [Android 0.4.12-android.1 testing build](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.12-android.1)
adds transfers for Original, Retro Rewind and Retro Rewind (Separate Save).
Root access is not needed:

1. Copy the PC's raw Mario Kart Wii `rksys.dat` to Downloads on the phone.
   Use a copy and keep the PC original. A Wii `data.bin`, ghost `.rkg`, save
   state, or whole NAND archive is not the supported input.
2. Open Original Mario Kart Wii, then **••• → Game Data & Saves → Manage
   Saves…**, select the matching profile, and use **Export Save Backup…** first
   if you have existing progress.
   Export is enabled once an initialized, valid save exists.
3. Choose **Restore Save Backup…**, check the destination profile, then
   **Choose Backup…** and select the copied `rksys.dat`.
   KartPad validates its size, header, and checksum before staging it.
4. Choose **Restart Now** to apply it before gameplay starts. KartPad also
   retains a backup of the previous save. Check the expected licenses and
   progress offline before continuing.

**Older public build:** `0.4.10-android.1` always targets the Original PAL save,
even when opened while playing Retro Rewind. It does not export or restore Retro
Rewind saves. Update to the testing build for that transfer; keep the PC copy and
verify the resulting progress offline. The reporter's PC WiiCompiled → Android
Retro Rewind/PAL test restored the license, but its online rating showed 5000;
complete migration acceptance remains open in
[#105](https://github.com/chrissotraidis/kartpad/issues/105).

**Profile selection:** Choose **Original Mario Kart Wii**, **Retro Rewind**, or
**Retro Rewind (Separate Save)** to match the source. The third profile is for
Retro Rewind's Separate Save option. Each export and restore targets only that
profile. Android activity recreation retains the file-picker destination;
missing or invalid destination state rejects the result. Existing pending
Original restores remain compatible. Transfers are manual; automatic Syncthing
synchronization is not implemented.

The importer accepts a raw 2,867,200-byte `RKSD0006` save with a valid core
checksum. That validation does not prove cross-region, cross-mod, or online
identity compatibility. A save transfer does not transfer the Mii database or
console identity. If the source is Dolphin, another WiiCompiled build, or
Retro Rewind, name it and the game region when asking for migration help.
Never post the save or NAND publicly.

**Retro Rewind ratings:** This picker transfers only `rksys.dat`. Retro Rewind
also stores ratings in `RRRating.pul`, keyed by online profile, and synchronizes
them during login. That companion file is not currently included in the transfer.
A restored license therefore does not establish a complete Retro migration.
If the rating differs, preserve the source save and Retro folder, stop rated
races on the migrated copy, and report source/game versions and whether the
friend code matches locally (only “same” or “different”; do not post the code).
Do not edit ratings, regenerate identity, or publicly upload rating/Mii files.
The missing companion file is a known gap; it does not by itself prove the
cause of any particular server rating. See [#105](https://github.com/chrissotraidis/kartpad/issues/105).

On Mac, **Data → Show KartPad Data** opens KartPad's support directory. Quit
the game before backing it up. On Apple TV, use
[`backup-tvos-state.sh`](../scripts/backup-tvos-state.sh) as described in the
[testing guide](TVOS-TESTING.md); its cache storage can be purged by tvOS.
Android's save-picker instructions do not imply the same UI exists on Apple.

## Display and performance

**Original 4:3** and **Widescreen 16:9 (Experimental)** fit the selected aspect
inside the display; black bars can be expected. **Fill Screen (Experimental)**
uses the actual surface aspect and dynamic game projection. It is intended to
expand the view, but some scenes or HUD elements may still distort. It is not
a guarantee that every screen renders correctly at a phone's wider aspect.

For a stretched image, compare 16:9 and Fill Screen on the same track and
camera view. Include both screenshots, app/OS version, and whether it affects
the 3D world, menus/HUD, or both. Use 4:3 or 16:9 as a temporary workaround.
See [#101](https://github.com/chrissotraidis/kartpad/issues/101).

For Android geometry/texture corruption, try a repeat of the same race at
**1x Native / Original 4:3** and report whether the defect persists. Include
phone/OS details, Original versus Retro Rewind, track and character/vehicle,
and startup renderer information plus warnings from the logs below. On a
foldable, include the screen in use and whether folding/resizing preceded the
failure. Similar GPU names do not establish identical drivers or a root cause.
See [#102](https://github.com/chrissotraidis/kartpad/issues/102) and
[#104](https://github.com/chrissotraidis/kartpad/issues/104).

For frame drops, enable KartPad's **Show FPS Counter** and compare a cold and
repeat run of the same course. Record resolution, aspect, game/battery mode,
time since launch, charging state, and whether audio also stutters. Samsung
Game Booster/Game Booster+ battery-saver settings affected the report in
[#103](https://github.com/chrissotraidis/kartpad/issues/103). Compare settings
one at a time; higher resolution is not an established performance fix.
Low GPU utilization alone cannot distinguish CPU, shader compilation,
presentation, or power/thermal limits.

AirPlay mirroring and a dedicated external-display game view are different
paths. Neither is currently accepted as supported iPhone/iPad output.
For black video with working audio, report the phone/tablet and OS, TV/monitor,
cable/adapter or AirPlay receiver, whether the device itself keeps rendering,
and whether connecting before versus after game launch changes the result.
See [#100](https://github.com/chrissotraidis/kartpad/issues/100).

## Collect a useful report

The [Android 0.4.12-android.2 diagnostic beta](https://github.com/chrissotraidis/kartpad/releases/tag/v0.4.12-android.2)
adds optional **Renderer Validation** on the chooser, off by default. When
requested for a graphics report, compare the same scene/settings with it off
and on, then turn it off for normal play. It enables actual game-renderer
validation and bounds protection and may slow gameplay; it is not a fix.
The private export also includes bounded `process-exits.json` OS metadata on
Android 11+. A missing record does not establish no crash, and a manual stop
can produce a user-requested exit. Review before sharing; do not upload the
whole archive. See the [beta test steps](releases/v0.4.12-android.2.md).

**Android:** **••• → Report a Problem… → Share Report…** produces a short
version/device/profile summary and your answers. It does **not** include the
runtime/renderer log history. For that history, reproduce once, fully close
KartPad from Recents, reopen to the Original/Retro Rewind chooser, and choose
**Export Private Diagnostics… → Save Locally…**. Open the ZIP locally; review
its `README.txt` and relevant `Logs/` text. Share only the startup renderer
lines, warnings/errors and a short interval around the failure. For performance,
include matching `KartPadPerf`/CPU/GPU and `android-health.log` intervals where
available; these use elapsed time since boot. Unavailable metrics are not zero.

The private ZIP can contain local paths and personal details. Do not upload it
raw. Remove usernames, private paths, IP/MAC addresses, console/account IDs,
friend codes, tokens, and other personal data from excerpts. Do not clear logs
or app storage before collecting them. No USB debugging or root is needed.

**iPhone/iPad:** **••• → Report a Problem… → Share Report…** creates the bounded
technical report. Review it and attach it to the existing issue with a
screenshot if relevant. **Report on GitHub** prefills the form but does not
attach the report file; its report ID alone is not a log upload.

**Mac:** **Help → Save Diagnostics Report…** creates a bounded report with
settings and current/previous session tails. Review it before attaching.

**Apple TV:** follow [`collect-tvos-diagnostics.sh`](../scripts/collect-tvos-diagnostics.sh)
and the [testing guide](TVOS-TESTING.md), then share reviewed, relevant excerpts.

For any platform, a report should say what happened, what you expected, how
to repeat it, and the exact version and hardware. Game images, extracted game
files, saves, complete app containers/NAND, signing material, and identities
do not belong in a public issue.

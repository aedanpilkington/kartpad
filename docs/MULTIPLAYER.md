# Multiplayer and controllers

Open **••• → Multiplayer…** on iPhone/iPad, **Controls → Multiplayer…** on
Mac, or **Multiplayer…** on the Apple TV game chooser. The same options cover
Original Mario Kart Wii and Retro Rewind.

## Local split-screen

Pair each controller in the operating system, select Multiplayer in the game,
and press the mapped A button on every pad at Register Controllers. iPhone/iPad
Controller Setup lists the four stable player slots and offers face-button
remapping. Touch shares Player 1 with the first physical controller. Disconnecting
a controller leaves the other players in their existing slots.

Issue #85 was caused by WPAD's connection probe consulting keyboard slots while
KPAD input reads also consulted Apple's physical controllers. The probe now
checks the same physical slots without consuming registration button presses.
The callback also samples immediately instead of adding another asynchronous
read that could lose a short press. The regression fixture fails against the
0.4.8 prepared source and passes after the patch. Apple snapshot-controller tests
cover four slots, repeated connection probes, short A presses, disconnect and
reconnect. These tests do not establish a physical three-controller race.

## DualShock and DualSense

DualShock 4, DualSense and DualSense Edge can pair with supported Apple devices
using [Apple's pairing instructions](https://support.apple.com/en-us/111100).
KartPad's Apple mobile input bridge accepts Extended Gamepads, including these
controller families. The default positions are Cross = A, Circle = B,
Square = X and Triangle = Y. Customize Face Buttons changes those assignments;
the D-pad, sticks, Menu and triggers remain direct. Mac uses its existing SDL
controller setup. Pairing and gameplay with each exact model remain hardware
checks, separate from the snapshot-controller regression tests.

## GameCube controllers on iPad

A GameCube-shaped controller or adapter can use this path **if iPadOS exposes it
as an Extended Gamepad**. An original GameCube controller plugged into a
Nintendo/WUP-028 USB adapter is not supported by KartPad's current iPad input
bridge. This update does not add a raw USB GameCube adapter driver. The original
controller, an adapter's operating mode, and a Bluetooth GameCube-style pad are
not interchangeable support claims.

## Private friend rooms

The existing Wii friend-room flow lives inside the game: **Nintendo WFC →
Friends**. Everyone must use the same game profile, content version and online
service. Exchange friend codes; the host creates a room and friends join from
their friend roster. Retro Rewind normally uses Retro WFC. Original Nintendo WFC
is closed.

This is different from MeleePad's Dolphin/ENet synchronized-input rooms.
MeleePad room codes, native host/join controls, traversal, ready state and peer
chat have **not** been ported. A shared menu does not establish that parity.

## Experimental private Wii-server routing

**Private Wii Server…** saves a hostname or IPv4 address for the next launch.
Quit/fully close and reopen KartPad after saving or restoring the default.
The launch-time route is shared by both game profiles. This is a client route,
not a server installed or hosted by KartPad, and is not a room-code service.

Only game DNS names below `nintendowifi.net` and `play.rwfc.net` are redirected.
Content downloads and peer IP addresses keep their original destination. The
explicit private service uses the legacy plaintext Wii WFC protocol; unrelated
TLS requests retain their normal certificate validation. Use a service you
trust. The setting does not weaken server-side authentication.

A compatible backend must implement the game's NAS authentication, GameSpy
presence/friends/matchmaking, NAT negotiation, and race services. Standard service
ports must be available, including the initial logical HTTPS connection on 443
and legacy HTTP on 80. A generic web server or another player's app IP is not
sufficient. A backend requiring a downloaded PowerPC patch or a different
signature/payload is not automatically compatible: KartPad does not compile or
execute new downloaded game code. In particular, entering a WiiLink/Wiimmfi
address alone does not establish Original Mario Kart Wii compatibility.

The new routing path has host validation, destination-scope and compiled Apple
transport coverage. Login, a hosted/joined friend room, complete races and
reconnect on an exact compatible backend still require acceptance, especially
for Original Mario Kart Wii. Do not describe this setting as verified private
online gameplay or MeleePad parity.

## Android handoff

Android work remains paused on `codex/android-a4-touch-settings`. No Android APK
is produced by this Apple update. Carry the shared private-service header and
runtime patch into the Android preparation when that branch resumes, expose
matching launch-time preferences in its Multiplayer dialog, and verify the
Android TLS path applies the same scoped legacy-service policy.

The paused Android controller patch already has its own SDL connection query.
When replaying `wiicompiled-android-sdl-controller.patch`, retain the new Apple
connection branch in `KPAD_IsKeyboardChannelConnected` and add the Android query
under its Android guard. Do not resolve that hunk by dropping either platform's
physical-controller detection. Repeat Android controller registration, stable
slots, mapping, lifecycle and two-/three-player acceptance before any APK release.

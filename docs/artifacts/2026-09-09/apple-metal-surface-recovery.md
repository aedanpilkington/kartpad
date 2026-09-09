# Apple Metal surface recovery correction

9 September 2026. Based on main `1e10db7`. This follows the
[ownership investigation](external-display-surface-ownership.md) associated with
[#100](https://github.com/chrissotraidis/kartpad/issues/100).

## Reproduced defect and correction

Aurora's Metal descriptor helper created a new SDL Metal view on every call.
WebGPU surface recreation calls this helper again. SDL UIKit replaces its root
view, leaving children of the previous root detached; on macOS, repeated calls
also allocate additional Metal views. Existing KartPad overlay reattachment on
activation/screenshots does not make ownership during surface recovery correct.

`aurora-metal-view-lifetime.patch` owns one SDL Metal view through a window
property and reuses it for subsequent descriptors. Its cleanup destroys the
view when SDL cleans up the window properties, before native-window destruction
in pinned SDL 3.4.4. Descriptor lifetime does not own the view. Creation,
property-registration and missing-layer failures return no descriptor; cleanup
also covers failed registration, as required by
[SDL's cleanup contract](https://wiki.libsdl.org/SDL3/SDL_SetPointerPropertyWithCleanup).
No retry loop, additional game window or new external-display mode is introduced.

The Mac and iOS preparation scripts apply the patch. tvOS inherits iOS
preparation. Android's shared preparation can contain the source patch, but its
renderer does not compile the Apple-only MetalBinding.mm implementation.
No Android runtime behavior or optimization is changed.

## Validation

The checked-in native probe compiles the actual prepared MetalBinding.mm against
real SDL 3.4.4 and Dawn headers. It attaches an ordinary native button below the
SDL root, requests 100 descriptors per window for three successive windows,
checks root/overlay/layer identity, clears and presents a real Metal drawable,
and verifies the layer is released after each window's teardown. UIKit release
is checked after bounded run-loop settling to allow appearance transitions.

| Target | Result |
| --- | --- |
| Native Apple Silicon macOS | 300 repeated descriptor requests, three successful Metal presentations and three layer releases pass |
| iPad Pro 13-inch (M5), iPadOS 26.5 simulator | Same native probe passes |
| iPhone 17 Pro, iOS 26.5 simulator | Same native probe passes |
| Unpatched helper, macOS and iPhone simulator | Negative controls fail at the first repeated request because the Metal layer is replaced |
| tvOS device target | Native probe compiles and links with the existing SDL library; Mach-O TVOS, minimum 17.0; not run on Apple TV |
| Injected SDL failures | Actual patched helper passes ASan/UBSan coverage for null window, property/create/register/layer failures, retry, descriptor destruction, independent windows and window-ID reuse |
| Existing Apple contracts | 23 iOS, 10 tvOS and eight macOS Python tests pass |

Fresh iOS/tvOS preparation completes. macOS preparation applies all patches and
reaches configuration; full-game configuration/build was deliberately stopped.
All three prepared Metal helpers have SHA-256
`451345117f3968eb3bf900da6baee9503226ac4032a475f8c8358171ebca1109`.
The probes contain no translated game code or game assets and use a separate
bundle identifier. The physical iPad/iPhone apps and their data were not changed.

## Reproduction

Run `python3 -m unittest discover -s tests -p test_apple_metal_view_lifetime.py`.
Build the native probe using `scripts/build-apple-metal-surface-probe.py` with
`--aurora` pointing at a prepared Aurora directory, `--sdl-include`,
`--sdl-library`, `--dawn-include`, `--sdk` and a fresh `--output` directory.
Run the macOS executable directly; simulator apps use bundle identifier
`dev.kartpad.metal-recovery-probe` and can be installed/launched with `simctl`.
Pass the unpatched Aurora directory for the negative control. No game import or
installed KartPad data is necessary.

## Acceptance boundary

These are native helper/recovery-path tests, not a forced Dawn surface-loss
result inside a running game. They establish the view-ownership fix, not the
cause or resolution of TV-only black video in #100. Full-game surface recovery,
touch/controller continuity, wired mirroring, AirPlay and Apple TV gameplay
remain separate acceptance gates. No IPA or game release is produced here.

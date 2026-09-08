# Android preview 1 candidate notices review

Reviewed the frozen clean source `cecd69c504f66aa0d8a40f485406618b9b616791`.
This review changes documentation and the notices packager only; it does not
rebuild or relabel the candidate. The coordinator owns integration, final tag,
publication and anonymous download verification. No physical device was touched.

## Exact candidate

| Artifact | Bytes | SHA-256 |
| --- | ---: | --- |
| KartPad-v0.4.13-android-preview.1-arm64.apk | 110351769 | `e70ff84fb64ce52294ac13608530bdbed3e22395e5fab7a8ef85799c22de0607` |
| Local app-release.aab | 91254598 | `bb1143228a476c89be78eb55187ac855dae133c435b0ff24f58543e5f1f36855` |

Version `0.4.13-android-preview.1`, code 28, package `dev.kartpad.android`.
Public signer SHA-256:
`c1dbe0a0d72d830a5779476b346a750d0a37515adef992cad2f3863058f7f2f2`.

| Stripped ARM64 library | SHA-256 |
| --- | --- |
| libmain.so | `be6833ba835fe5bbd0a93664bcaef50e7387a41b4834b26a8603a132eeca6029` |
| libkartpad_discio.so | `0e5bd27501b1aee71db63364f0673682e0cca3c0234d560d4c54ac87e01c0d0b` |
| libSDL3.so | `d7a17c375adcb71818210581b885f59832d5f95b663aa7a7d493484a00a94753` |
| libc++_shared.so | `c4c2fe5cbcb1fba0003a31fc7ab29a9bb12df6cc187ec45a806462540e83d93b` |

Independent APK and unsigned-AAB audits pass, including exact resource/library
and permission allowlists, forbidden payload checks and 16-KiB alignment.
All four stripped native build outputs compare byte-for-byte with the APK.
The independently verified certificate matches the existing public signer.
The unchanged DiscIO library retains the checked-extraction implementation.
The new native library includes the runtime diagnostic/scalar-helper changes;
no performance or graphics-fix acceptance follows from that identity.

## Source relationship

Recomputed the complete embedded provenance from the frozen source and its
actual prepared runtime/translation inputs; it matches the APK byte-for-byte.
CMake points to the prepared floating-point experiment runtime used for this
candidate, not an assumption that repository HEAD itself is a native binary.

- Repository: 1047 files, SHA-256 `10414286dc08dfba6ded0ce7c3eb415b0b8e51b78af55aed4edc8f46a600d433`.
- Prepared runtime: 876 files, SHA-256 `9d4fa491d7376619bc1eefbd87a35e161dc3c29e530fabe8faf2b945aafcd1f2`.
- Translation: 30030 files, SHA-256 `536f852b1f9f03af6038902368669f46f4ec7d1dbc1f06d5e37c24444b353a4c`.

These are source-input fingerprints, not proof of dependency identity or
hardware behavior. The notices packager pins the exact APK/AAB and all four
native hashes, checks embedded clean source, and compares APK assets/native/DEX
against the AAB. It records `sourceCommit` separately from
`packagingSourceCommit` and rejects intervening changes outside docs/the
notices script. The final release tag must not misstate this relationship.

## Notices and remaining gates

WiiCompiled and Dolphin source checkout revisions match their dependency-lock
pins. Native dependency notices come from this candidate's CMake dependency
tree, with existing pinned SDL/Dawn/Mbed TLS/Minizip inputs. The packager retains
an explicit text-only allowlist and adds the actual FreeType Project License
plus attribution; the prior package included only its license index.
It never copies the AAB, translated inputs, game data or signing keys.

The reviewed rating fixes include checked backup/publication barriers,
conservative NAND refusal and durable terminal records. Existing executable
checks pass: 214 rating storage, 52 format, save/identity suites and eight
runtime TOML refusal probes. No remaining concrete rating data-safety blocker
was found in that scoped engineering review. Power-loss behavior has not been
physically reproduced or accepted on Android storage.

Packaging-owner evidence is separate: same-AAB alternate-signer release APK
emulator startup/menu/export, nine-entry diagnostics archive (42293 bytes)
with matching source manifest, and 21 byte-identical synthetic state files.
The public-signer APK has no physical preview acceptance. Real-save offline
rating verification, Mii migration, sustained performance, Adreno graphics and
successful online racing remain open. Corresponding Source and game-content
rights obligations are not certified by an allowlisted notices archive.

Release checklist gates outside this notices pass: fresh runtime preparation
compared with the actual compiled inputs; current repository/source-pin and
semantic/native/controller checks; independent signed APK derivation; and
public-signer emulator acceptance. Fingerprints alone do not replace these.
The coordinator must resolve and record those gates before publication, then
verify anonymous downloads. No second signing operation or device installation
was performed by this review.

# Candidate 63 source-package assessment

This is a bounded technical inventory, not a complete source-distribution or
rights certification. It does not change the APK, game settings or phone state.
The candidate APK and its 28-entry notices package remain those recorded in
[the verification record](android-release63-verification.md).

## What was verified

The local repository archive for application commit
`6a2dffc30f8f0d55a7eb928c614c88e054240d14` has SHA-256
`2e9c689f0b4b7aad4130c2a6f268f112d7e2bc5af663ec6224201da211db00c5`.
All **1,117 archived files** exactly match their Git blob identities at that
commit; none are added from the working tree or ignored build directories.
The archive includes existing tracked documentation images and the Gradle
wrapper JAR as well as application source, patches, recipes and dependency pins.

Path checks found no private/build/bootstrap paths, traversal, links, or game,
package and signing-file extensions. A focused content scan found no structured
private-key blocks, GitHub/AWS credential patterns, or ELF binaries. This narrow
scan is not a guarantee that arbitrary secrets or rights-sensitive content are
absent. The archive remains outside the proposed APK/notices download directory.

## What the repository archive does not contain

The actual release CMake compilation database contains the following command
groups. These count translation-unit compile commands, not total source files
or a complete inventory of headers, generated includes and linked libraries.

| Source group | Compile commands | Repository archive coverage |
| --- | ---: | --- |
| Tracked KartPad integration | 14 | Source included |
| Prepared upstream/runtime | 244 | Upstream checkouts omitted; tracked patches/recipe included |
| CMake dependency source | 267 | Dependency checkouts omitted |
| Bootstrapped dependency source | 127 | Dependency checkouts omitted |
| Private translation/game-derived generated input | 237 | Generated graph omitted |
| Other generated/dependency source | 17 | Requires individual mapping |

The embedded build provenance separately fingerprints 877 prepared-runtime
files and 30,030 translation-input files. A pin or fingerprint identifies an
input; it does not supply its contents. Prebuilt Dawn, SDL and the retained
DiscIO dependency also need their source/build provenance considered outside
this main-library compilation database. The normal build guide reconstructs
inputs using the pinned bootstrap and an owner's supported game image; that
recipe has not been independently reproduced from this source archive alone.

## Concrete remaining work

1. Map the remaining generated inputs and every linked dependency to retained
   source, tracked modifications and the exact build recipe. Do not substitute
   an upstream version merely because its name matches.
2. Determine the complete source delivery required for this binary, then supply
   the missing covered source through the project's documented distribution
   process. Do not upload private game-derived inputs to resolve the gap by
   assumption. The existing [rights/source policy](../../../RIGHTS_AND_LICENSES.md)
   remains applicable.
3. Verify reconstruction from the proposed source delivery with independently
   supplied supported game inputs where the recipe requires them. Keep this
   distinct from repeating the APK derivation, which already passed.

This turns the generic source-review gate into an explicit inventory. It does
not add an FPS improvement, clear physical acceptance, or authorize publication.
No phone input, install, build, emulator restart or public message was needed
for this assessment. Private machine-readable evidence is retained locally in
`build/release-final/repository-source-content-audit.json`.

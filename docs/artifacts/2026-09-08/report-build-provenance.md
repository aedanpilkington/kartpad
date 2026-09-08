# Report build provenance integration in progress

Isolated branch codex/report-build-provenance; installed Pixel comparison
candidate a850ada and its builds/data remain untouched.

Current reports contain app version/profile/content context but lack a
reliable source/runtime fingerprint. New write-build-provenance.py emits a
deterministic manifest with source revision, dirty state, source-tree hash,
prepared-runtime hash and full translation-tree hash. Only counts/hashes and
revision metadata are emitted; paths and input contents never enter JSON.
The manifest explicitly excludes dependency/binary identity claims. Symlinks
inside inputs are rejected instead of traversing unrelated directories.

Two executable unittest cases pass (0.190s), covering clean/dirty/new/deleted
source, ignored build output, deterministic repeated output, path/content
privacy, relocation, renamed inputs, unavailable inputs and symlink rejection.
A real invocation against the retained Pixel prepared source and full
translation graph succeeds, with JSON retained privately in build/.

Android preBuild now generates an immutable asset; iOS simulator/device build
scripts generate before compilation and copy it into the unsigned app before
audit/signing. Both report readers cap input at8193bytes, accept at most8192,
validate revision/hash/count/boolean fields, and rebuild an allowlisted object.
Missing/malformed/oversized manifests yield null; unknown fields are omitted.
APK/AAB resource allowlists accept only the added kartpad-build.json name.

Validation: Android compileDebugKotlin + mergeDebugAssets + lintRelease passed
in35s (49tasks,24executed,25fromcache). Generated/merged manifests match exactly
(480bytes, explicit dirty source during development, real runtime/translation
fingerprints). Kotlin and Foundation executable tests passed in5.300s, including
actual synthetic Foundation bundle resource lookup, missing/oversized fallback,
malformed hashes and private-field omission. iOS ARM64/min16 SDK syntax with
-Werror passed. Shell syntax/diff checks pass. Android66 contract tests passed
after retaining the existing icon dependency declaration as a separate call;
the initial literal-string icon contract failed on an equivalent combined call.

No full game APK/AAB or iOS app was rebuilt for this report-only follow-up, and
no device report acceptance is claimed. The installed candidate does not contain
this manifest. Full artifact resource/signing audit and physical report/share
checks remain. Generated output must be outside fingerprinted inputs and
ignored by the source inventory.
No public reply, release or IPA.

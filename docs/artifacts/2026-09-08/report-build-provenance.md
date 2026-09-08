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

Not integrated yet: Android generated assets, iOS unsigned app resource
packaging, bounded report readers, package/audit allowlists and report tests.
Do not claim the installed candidate contains this manifest. Ensure generated
output is outside fingerprinted inputs (and ignored by the source inventory).
No public reply, release or IPA.

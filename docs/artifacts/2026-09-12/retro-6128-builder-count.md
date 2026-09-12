# Retro Rewind 6.12.8 personal-builder function count

The pinned 6.12.8 profile expected 4,188 mod functions, but its supported inputs
produce 4,095. This made `kartpad_builder.pipeline.translate` reject both a new
translation and reuse of its cached output with `translation failed profile
validation`. Correct only `translation.expectedRetroFunctions` to 4,095; retain
all version, artifact hash, generated-function and base-function pins.

Evidence from the fresh issue #196 translation on main
`f0fdbceb1062f18b8161b25192ec0f2eb387991f`, inspected 12 September 2026:

- Staged `Code.pul` SHA-256:
  `88cd25ff08121f7c4ddb40538703f40c270f414f2dbc55a6b4b6767e62db7253`,
  matching the checked-in 6.12.8 profile.
- 29,637 generated `func_*.cpp` files; emitted graph counts 29,065 active base
  functions and 4,095 Retro Rewind functions, with Retro Rewind shards enabled.
- The prepared translator's `TranslatedBuildShardEmitter` supplies
  `modRecords.Count` to both the CMake manifest and its reported mod-function
  count. Its completed translation log independently reports 4,095.
- Read-only invocation of the real builder's cached validation against that
  output rejects the old 4,188 profile and accepts the corrected profile. Both
  the standalone and actual compiled-shard REL report guards pass.
- The regression fixture uses the real profile's mod-count pin and real guard
  validation: 4,095 succeeds, while 4,188, 4,094 and 4,096 are rejected.
- REL report regression suite: 11 tests, one skip for unavailable pinned runtime
  in the isolated worktree. Builder suite: 25 tests, one skip for absent private
  production payload. All executed tests pass; `git diff --check` passes.

No translator regeneration, app rebuild, device installation or gameplay
acceptance is claimed by this correction. The existing strict count checks
remain unchanged. Future mod updates must remeasure this count alongside their
artifact pins.

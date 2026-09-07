# iPad identity clarity — 0.4.10 build 25

The maintainer tested build 24 on iPad and reported that returning to the chooser,
reopening to switch games, and deleting and creating Retro Rewind licenses
worked. The remaining confusion concerned identity versus license editing,
slot numbering, and applying pending changes.

Build 25 renames the iPhone/iPad actions to **Rename or Delete Licenses…** and
**Edit Mii Name…**. The identity summary and empty-license state explain that a
Mii supplies a name and appearance, while a game license stores progress and a
friend code. New licenses are created through **New** inside the game.
Deletion affects only its selected slot; other licenses retain their slots.

Pending-change messages explicitly require fully closing KartPad from the app
switcher and reopening it. Returning to the KartPad menu and resuming does not
apply them. Identity result alerts now use the existing transition-aware alert
presenter so their confirmation waits for the previous alert to dismiss.

Validation:

- 68 Python checks and 17 native tests passed.
- Fresh preparation reproduced the runtime source, with only the intended
  build-number increment from 24 to 25.
- Physical iOS/iPadOS compilation and the full app audit passed. The audit's
  expected name-editor label was updated to **Edit Mii Name**.
- The rebuilt iPad Simulator showed the new identity action labels, explanatory
  text, and Mii name editor. Saving the unchanged test Mii name displayed the
  **Mii Name Scheduled** confirmation with the full-close/reopen instruction.
- Build 25 was signed locally, installed in place on the attached iPad, and
  relaunched. The installed version and live process were verified. All 407
  protected files were byte-identical after installation. After launch, saves
  and preferences still matched; Config.toml was rewritten with equivalent
  values. No earlier save backup was restored over the maintainer's new license.

The new IPA is an unsigned iPhone/iPad artifact. Mac and experimental Apple TV
remain on 0.4.9, and Android development remains paused. Native private-room
transport and safe in-process game switching remain separate unfinished work.

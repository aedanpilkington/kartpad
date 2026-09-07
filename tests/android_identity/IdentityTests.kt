package dev.kartpad.android

import java.io.File
import java.nio.file.Files
import java.util.zip.CRC32
import android.util.AtomicFile

fun main(args: Array<String>) {
    System.load(args[0])
    val fixtures = File(args[1])
    val root = Files.createTempDirectory("kartpad-identity-test-").toFile()
    fun path(profile: String) = File(root, "KartPad/${KartPadIdentityStorage.paths.getValue(profile)}")
    for (profile in KartPadIdentityStorage.paths.keys) {
        path(profile).parentFile.mkdirs()
        File(fixtures, if (profile == "mii") "mii.dat" else "save.dat").copyTo(path(profile))
    }
    fun record(profile: String) = KartPadIdentityStorage.records(root, profile == "mii").first { it.profile == profile }
    fun crc(bytes: ByteArray) {
        val crc = CRC32().apply { update(bytes, 0, 0x27ffc) }.value
        repeat(4) { bytes[0x27ffc + it] = (crc shr (24 - it * 8)).toByte() }
    }
    val original = path("original").readBytes()
    KartPadIdentityStorage.stage(root, record("original"), false, "Racer")
    check(path("original").readBytes().contentEquals(original))
    check(runCatching { KartPadIdentityStorage.stage(root, record("original"), false, "Other") }.isFailure)
    val latest = original.copyOf().apply { this[8 + 0x90] = 0x44; crc(this) }
    path("original").writeBytes(latest)
    check(KartPadIdentityStorage.applyPending(root) == null)
    check(record("original").name == "Racer")
    val renamed = path("original").readBytes()
    for (i in latest.indices) if (i !in (8 + 0x14) until (8 + 0x14 + 20) && i !in 0x27ffc..0x27fff)
        check(latest[i] == renamed[i])
    check(record("retro_rewind").name == "Player")
    KartPadIdentityStorage.stage(root, record("mii"), false, "Both")
    AtomicFile.failSuffix = "/data/rksys.dat"
    check(KartPadIdentityStorage.applyPending(root) != null)
    check(KartPadIdentityStorage.hasPending(root))
    AtomicFile.failSuffix = null
    check(KartPadIdentityStorage.applyPending(root) == null)
    check(KartPadIdentityStorage.records(root, false).all { it.name == "Both" })
    check(record("mii").name == "Both")
    val beforeDelete = path("original").readBytes()
    KartPadIdentityStorage.stage(root, record("original"), true, "")
    check(KartPadIdentityStorage.applyPending(root) == null)
    check(KartPadIdentityStorage.records(root, false).count { it.profile == "original" } == 1)
    val deleted = path("original").readBytes()
    check(beforeDelete.copyOfRange(8 + 0x8cc0, 8 + 2 * 0x8cc0)
        .contentEquals(deleted.copyOfRange(8 + 0x8cc0, 8 + 2 * 0x8cc0)))
    check(runCatching { KartPadIdentityStorage.stage(root, record("mii"), false, "01234567890") }.isFailure)
    check(!KartPadIdentityStorage.hasPending(root))
    check(File(root, "KartPad/IdentityBackups").listFiles()!!.size == 3)
    println("Android identity passed: JNI semantics, latest-progress preservation, slot isolation, linked profiles, interrupted transaction recovery, backups, invalid names")
    root.deleteRecursively() // Only this test's newly-created synthetic directory.
}

package dev.kartpad.android

import android.util.AtomicFile
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Base64
import java.util.UUID
import org.json.JSONObject

/** Manual companion restore to an existing Retro save; publication happens before SDL starts. */
internal object KartPadRatingStorage {
    private fun pending(files: File) = File(files, "KartPad/PendingRating.json")
    fun hasPending(files: File) = pending(files).isFile

    /** Chooser-only cancellation while no game is paused; preserves current ratings and backups. */
    fun cancelPending(files: File) {
        val file = pending(files)
        check(!file.exists() || file.delete()) { "The staged rating restore could not be cancelled." }
    }

    fun profileIds(save: ByteArray): Set<Int> {
        KartPadSaveStorage.validate(save)
        val data = ByteBuffer.wrap(save).order(ByteOrder.BIG_ENDIAN)
        val ids = linkedSetOf<Int>()
        repeat(4) { slot ->
            val license = 8 + slot * 0x8cc0
            if (data.getInt(license) == 0x524b5044) {
                val id = data.getInt(license + 0x40 + 0x1c)
                if (id != 0) {
                    require(id in 1 until 1_000_000_000 && ids.add(id)) {
                        "The save has an unsupported or duplicate online profile."
                    }
                }
            }
        }
        require(ids.isNotEmpty()) { "This save has no online profile to match ratings to." }
        return ids
    }

    private fun saveIds(files: File, profile: String): Set<Int> {
        require(profile == "retro_rewind" || profile == "retro_rewind_separate") {
            "Rating restore requires a Retro Rewind save profile."
        }
        return profileIds(KartPadSaveStorage.readActive(files, profile))
    }

    private fun target(files: File): File {
        val config = File(files, "KartPad/Config.toml")
        // Do not guess when a manually configured NAND may redirect runtime writes.
        require(!config.isFile || !Regex("(?m)^\\s*nand_root\\s*=").containsMatchIn(config.readText())) {
            "Rating restore is unavailable with a custom NAND configuration."
        }
        return File(files, "KartPad/NAND/shared2/Pulsar/RetroRewind6/RRRating.pul").also {
            require(it.isFile) { "Start Retro Rewind with this save and close it before importing ratings; no local rating file exists yet." }
        }
    }

    private fun readRating(file: File): ByteArray {
        require(file.length() == KartPadRatingCompanion.FILE_BYTES.toLong()) { "Unsupported rating file length." }
        return file.readBytes().also(KartPadRatingCompanion::validate)
    }

    fun stage(files: File, profile: String, source: ByteArray) {
        require(!KartPadSaveStorage.hasPending(files) && !KartPadIdentityStorage.hasPending(files)) {
            "Restart to apply pending save, rating or identity changes first."
        }
        val ids = saveIds(files, profile)
        // Validate the proposed merge now, but merge again against the latest destination at startup.
        KartPadRatingCompanion.merge(source, readRating(target(files)), ids)
        val request = JSONObject().put("profile", profile)
            .put("ids", ids.sorted().joinToString(","))
            .put("source", Base64.getEncoder().encodeToString(source))
        write(pending(files), request.toString().toByteArray())
    }

    fun applyPending(files: File): String? {
        val requestFile = pending(files)
        if (!requestFile.isFile) return null
        return runCatching {
            check(!KartPadIdentityStorage.hasPending(files))
            require(requestFile.length() <= 4096) { "Invalid pending rating restore." }
            val request = JSONObject(requestFile.readText())
            val ids = saveIds(files, request.getString("profile"))
            require(ids.sorted().joinToString(",") == request.getString("ids")) {
                "The save's online profiles changed after staging."
            }
            val source = Base64.getDecoder().decode(request.getString("source"))
            val active = target(files)
            val current = readRating(active)
            val replacement = KartPadRatingCompanion.merge(source, current, ids)
            val backup = File(files, "KartPad/SaveBackups/ratings-${UUID.randomUUID()}.pul")
            write(backup, current)
            write(active, replacement)
            check(requestFile.delete())
        }.exceptionOrNull()?.let {
            "The pending rating restore could not be completed. Gameplay is stopped; the staged request and any backups are retained."
        }
    }

    private fun write(file: File, bytes: ByteArray) {
        check(file.parentFile?.let { it.isDirectory || it.mkdirs() } == true)
        val atomic = AtomicFile(file)
        val stream = atomic.startWrite()
        try { stream.write(bytes); atomic.finishWrite(stream) }
        catch (error: Throwable) { atomic.failWrite(stream); throw error }
    }
}

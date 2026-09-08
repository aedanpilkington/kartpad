package dev.kartpad.android

import android.content.Context
import android.os.SystemClock
import java.io.File
import org.json.JSONObject

/** Bounded, structured context shared by the short report and private export. */
internal object KartPadReportContext {
    fun snapshot(context: Context, profile: String?, activeRendererValidation: Boolean? = null): JSONObject {
        val versionFile = File(context.filesDir, "KartPad/RetroRewind/${RetroRewindRelease.ROOT}/version.txt")
        val installed = runCatching { versionFile.inputStream().use { input ->
            val bytes = ByteArray(129)
            var count = 0
            while (count < bytes.size) {
                val read = input.read(bytes, count, bytes.size - count)
                if (read < 0) break
                check(read > 0)
                count += read
            }
            if (count > 128) null else String(bytes, 0, count, Charsets.UTF_8).trim()
                .takeIf { it.length <= 64 && Regex("[0-9]+(\\.[0-9]+){1,3}").matches(it) }
        } }.getOrNull()
        val state = when {
            !versionFile.exists() -> "not_installed"
            installed == null -> "unreadable_or_invalid"
            installed != RetroRewindRelease.VERSION -> "version_mismatch"
            else -> "version_match_only"
        }
        return JSONObject()
            .put("schema", 1)
            .put("platform", "android")
            .put("app_version", BuildConfig.VERSION_NAME)
            .put("app_build", BuildConfig.VERSION_CODE.toString())
            .put("runtime_profile", profile?.takeIf { it == "base" || it == "retro_rewind" } ?: "unknown")
            .put("captured_unix_ms", System.currentTimeMillis())
            .put("monotonic_ms", SystemClock.elapsedRealtime())
            .put("monotonic_clock", "android_elapsed_realtime")
            .put("retro_supported_version", RetroRewindRelease.VERSION)
            .put("retro_installed_version", installed ?: JSONObject.NULL)
            .put("retro_version_state", state)
            .put("retro_code_validation", "not_rechecked_for_report")
            .put("resolution_scale", KartPadTouchSettings.resolutionScale(context))
            .put("aspect_mode", KartPadTouchSettings.aspectMode(context))
            .put("renderer_validation", activeRendererValidation ?: JSONObject.NULL)
            .put("renderer_validation_configured", KartPadRendererDiagnostics.enabled(context))
    }
}

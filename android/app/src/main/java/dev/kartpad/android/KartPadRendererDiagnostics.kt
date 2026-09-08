package dev.kartpad.android

import android.content.Context
import android.system.Os

/** Opt-in game-renderer checks. Configure before SDL creates the GPU device. */
internal object KartPadRendererDiagnostics {
    @Volatile var active = false
        private set

    fun enabled(context: Context): Boolean = context.getSharedPreferences("renderer_diagnostics", 0)
        .getBoolean("validation", false)

    fun setEnabled(context: Context, enabled: Boolean): Boolean =
        context.getSharedPreferences("renderer_diagnostics", 0).edit()
            .putBoolean("validation", enabled).commit()

    fun configure(context: Context) {
        active = enabled(context)
        Os.setenv("KARTPAD_RENDERER_VALIDATION", if (active) "1" else "0", true)
    }
}

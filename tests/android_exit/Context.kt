package android.content
import android.app.ActivityManager
class Context(val manager: ActivityManager?) {
    val packageName = "dev.kartpad.android"
    fun <T> getSystemService(type: Class<T>): T? = type.cast(manager)
}

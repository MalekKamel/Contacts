package app.common.presentation.permission

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionRequester(private val activity: FragmentActivity) {

    fun request(vararg permissions: String, callback: (Boolean) -> Unit) {
        when {
            hasPermissions(*permissions) -> callback(true)
            else -> PermissionFrag.request(permissions.asList(), activity, callback)
        }
    }

    private fun hasPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}
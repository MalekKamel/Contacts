package app.common.presentation.permission

import android.content.Context
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

internal class PermissionFrag : Fragment() {
    private lateinit var callback: (Boolean) -> Unit

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var permissions: List<String> = emptyList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val value = it.values.first()
            callback(value)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchPermissions()
    }

    private fun launchPermissions() {
        requestPermissionLauncher.launch(permissions.toTypedArray())
    }

    companion object {
        private fun newInstance(): PermissionFrag {
            return PermissionFrag()
        }

        fun request(permissions: List<String>,
                    activity: FragmentActivity,
                    callback: (Boolean) -> Unit): PermissionFrag {
            var frag = findFragment(activity.supportFragmentManager)
            if (frag == null) {
                frag = newInstance()
                frag.permissions = permissions
                frag.callback = callback
                activity.supportFragmentManager
                        .beginTransaction()
                        .add(frag, PermissionFrag::class.java.simpleName)
                        .commitAllowingStateLoss()
                return frag
            }
            frag.permissions = permissions
            frag.callback = callback
            frag.launchPermissions()
            return frag
        }

        private fun findFragment(fragmentManager: FragmentManager): PermissionFrag? {
            return fragmentManager.findFragmentByTag(PermissionFrag::class.java.simpleName) as PermissionFrag?
        }

    }
}
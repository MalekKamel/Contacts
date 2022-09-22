package app.common.presentation.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.sha.kamel.navigator.FragmentNavigator
import com.sha.kamel.navigator.NavigatorOptions

class AppNavigator(
    private val activity: FragmentActivity,
    @IdRes private val frameResourceId: Int = NavigatorOptions.frameLayoutId
) {

    init {
        NavigatorOptions.frameLayoutId = frameResourceId
    }

    fun add(fragment: Fragment, addToBackStack: Boolean = true) {
        activity.runOnUiThread {
            FragmentNavigator(activity).add(fragment, addToBackStack)
        }
    }

    fun replace(fragment: Fragment, addToBackStack: Boolean = true) {
        FragmentNavigator(activity).replace(fragment, addToBackStack)
    }
}
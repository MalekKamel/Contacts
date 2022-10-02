package app.common.presentation.ui.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import app.common.presentation.ui.activity.BaseActivity
import app.common.presentation.ui.view.ViewInterface
import app.common.presentation.ui.vm.AppViewModel

abstract class AppFragment<B : ViewBinding, VM : AppViewModel> : Fragment(), ViewInterface {

    abstract val vm: VM
    lateinit var binding: B

    abstract fun inflate(inflater: LayoutInflater, container: ViewGroup?): B

    open var swipeRefreshLayoutId: Int = 0

    open var hasBackNavigation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupVm()
    }

    private fun setupVm() {
        vm.toggleLoading.observe(this, Observer { show ->
            if (show) {
                showLoadingDialog()
                return@Observer
            }
            dismissLoadingDialogs()
        })
        vm.showError.observe(this) { showErrorInFlashBar(it) }
        vm.showErrorRes.observe(this) { showErrorInFlashBar(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, container)
        return binding.root
    }

    override fun activity(): BaseActivity? = activity as? BaseActivity

    fun <T : View> findViewById(@IdRes id: Int): T = requireActivity().findViewById(id)

}
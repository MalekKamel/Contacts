package com.contacts.app.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import app.common.presentation.navigator.AppNavigator
import app.common.presentation.ui.frag.AppFragment
import com.contacts.app.databinding.FragmentSplashBinding
import com.contacts.app.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sha on 7/28/20.
 */

class SplashFragment : AppFragment<FragmentSplashBinding, SplashViewModel>() {
    override val vm: SplashViewModel by viewModel()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupFlow()
    }

    private fun setupFlow() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToHome()
        }, 2000)
    }

    private fun navigateToHome() {
        AppNavigator(requireActivity()).replace(HomeFragment())
    }

}

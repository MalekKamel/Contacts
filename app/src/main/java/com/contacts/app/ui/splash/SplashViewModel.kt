package com.contacts.app.ui.splash

import app.common.data.Repos
import app.common.presentation.ui.vm.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Sha on 7/28/20.
 */

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

class SplashViewModel(repos: Repos) : BaseViewModel(repos)
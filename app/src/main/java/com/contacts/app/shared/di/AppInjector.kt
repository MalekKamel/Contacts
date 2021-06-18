package com.contacts.app.shared.di

import com.contacts.app.ui.home.homeModule
import com.contacts.app.ui.splash.splashModule
import org.koin.core.context.loadKoinModules

fun injectAppModule() = loadAppModules

private val loadAppModules by lazy {
    loadKoinModules(
            listOf(
                    splashModule,
                    homeModule
            )
    )
}
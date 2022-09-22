package com.contacts.app.shared

import app.common.core.app.CoreApp
import app.common.core.util.reportAndPrint
import app.common.data.work.AppWorkManager
import com.contacts.app.R
import com.contacts.app.shared.di.KoinInjector
import com.sha.kamel.navigator.NavigatorOptions

/**
 * Created by Sha on 13/04/17.
 */

class App : CoreApp() {

    override fun onCreate() {
        super.onCreate()
        KoinInjector.inject(this)
        AppWorkManager.startContactsWorker()
        NavigatorOptions.frameLayoutId = R.id.mainFrameLayout
    }
}

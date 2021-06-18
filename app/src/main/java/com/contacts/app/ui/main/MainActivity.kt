package com.contacts.app.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.contacts.app.R
import app.common.presentation.navigation.NavHostActivity

/**
 * Created by Sha on 7/28/20.
 */

class MainActivity : NavHostActivity() {
    override var navGraph: Int = R.navigation.nav_graph_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val host = NavHostFragment.create(navGraph)
        supportFragmentManager.beginTransaction()
                .replace(R.id.navHost, host)
                .commit()
    }
}

package com.contacts.app.ui.home

import android.Manifest
import android.os.Bundle
import android.view.View
import app.common.core.util.linearLayoutManager
import app.common.presentation.permission.PermissionRequester
import app.common.presentation.ui.frag.BaseFrag
import com.contacts.app.R
import com.sha.bulletin.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sha on 7/28/20.
 */

class HomeFragment : BaseFrag<HomeViewModel>() {
    override var layoutId: Int = R.layout.fragment_home
    override val vm: HomeViewModel by viewModel()
    private val adapter: ContactsAdapter by lazy { ContactsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeContacts()
        observeContactSync()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun onResume() {
        super.onResume()
        loadContacts()
    }

    private fun setupUi() {
        swipeRefresh.setOnRefreshListener {
            loadContacts()
        }
        rv.linearLayoutManager(context)
    }

    private fun observeContactSync() {
        vm.onSync.observe(this) { isModified ->
            if (!isModified) return@observe
            loadContacts()
        }
    }

    private fun observeContacts() {
        vm.contacts.observe(this) {
            rv.adapter = adapter
            adapter.list = it
            adapter.notifyDataSetChanged()
            syncContacts()
        }
    }

    private fun loadContacts() {
        PermissionRequester(requireActivity())
            .request(
                Manifest.permission.READ_CONTACTS
            ) {
                vm.loadContacts()
            }
    }

    private fun syncContacts() {
        vm.syncContacts()
    }

    override fun showLoadingDialog(content: String): LoadingDialog? {
        swipeRefresh.isRefreshing = true
        return null
    }

    override fun dismissLoadingDialogs() {
        swipeRefresh.isRefreshing = false
    }
}
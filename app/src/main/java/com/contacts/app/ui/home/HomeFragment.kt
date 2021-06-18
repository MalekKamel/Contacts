package com.contacts.app.ui.home

import app.common.core.util.linearLayoutManager
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

    override fun onResume() {
        super.onResume()
        setupUi()
        observeContacts()
        observeContactSync()
        loadContacts()
    }

    private fun setupUi() {
        rv.linearLayoutManager(context)
    }

    private fun observeContactSync() {
        vm.onSync.observe(viewLifecycleOwner) { isModified ->
            if (!isModified) return@observe
            loadContacts()
        }
    }

    private fun observeContacts() {
        vm.contacts.observe(viewLifecycleOwner) {
            rv.adapter = adapter
            adapter.list = it
            adapter.notifyDataSetChanged()
            syncContacts()
        }
    }

    private fun loadContacts() {
        vm.loadContacts()
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
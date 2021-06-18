package com.contacts.app.ui.home

import androidx.lifecycle.Observer
import app.common.core.util.linearLayoutManager
import com.contacts.app.R
import com.sha.bulletin.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_home.*
import app.common.data.model.ContactsRequest
import app.common.presentation.ui.frag.BaseFrag
import org.koin.android.viewmodel.ext.android.viewModel

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
        loadContacts(ContactsRequest())
    }

    private fun setupUi() {
        rv.linearLayoutManager(context)
    }

    private fun loadContacts(request: ContactsRequest) {
        vm.loadContactsPaged(request).observe(viewLifecycleOwner, Observer {
            rv.adapter = adapter
            adapter.submitList(it)
        })
    }

    override fun showLoadingDialog(content: String): LoadingDialog? {
        swipeRefresh.isRefreshing = true
        return null
    }

    override fun dismissLoadingDialogs() {
        swipeRefresh.isRefreshing = false
    }
}
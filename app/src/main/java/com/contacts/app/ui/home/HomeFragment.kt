package com.contacts.app.ui.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.common.core.util.linearLayoutManager
import app.common.presentation.permission.PermissionRequester
import app.common.presentation.ui.frag.BaseFrag
import com.contacts.app.databinding.FragmentHomeBinding
import com.sha.bulletin.dialog.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sha on 7/28/20.
 */

class HomeFragment : BaseFrag<FragmentHomeBinding, HomeViewModel>() {
    override val vm: HomeViewModel by viewModel()
    private val adapter: ContactsAdapter by lazy { ContactsAdapter(this) }

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

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
        binding.swipeRefresh.setOnRefreshListener {
            loadContacts()
        }
        binding.rv.linearLayoutManager(context)
    }

    private fun observeContactSync() {
        vm.onSync.observe(this) { isModified ->
            if (!isModified) return@observe
            loadContacts()
        }
    }

    private fun observeContacts() {
        vm.contacts.observe(this) {
            binding.rv.adapter = adapter
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
        binding.swipeRefresh.isRefreshing = true
        return null
    }

    override fun dismissLoadingDialogs() {
        binding.swipeRefresh.isRefreshing = false
    }
}
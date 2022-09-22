package com.contacts.app.ui.home

import androidx.lifecycle.MutableLiveData
import app.common.data.Repos
import app.common.data.model.ContactItem
import app.common.presentation.ui.vm.BaseViewModel
import com.sha.coroutinerequester.RequestOptions
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Sha on 7/28/20.
 */

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

class HomeViewModel(dataManager: Repos) : BaseViewModel(dataManager) {
    val contacts = MutableLiveData<List<ContactItem>>()
    val onSync = MutableLiveData<Boolean>()

    fun loadContacts() {
        request {
            val response = dm.contacts.contacts()
            contacts.postValue(response)
        }
    }

    fun syncContacts() {
        val options = RequestOptions.Builder()
            .showLoading(false)
            .build()
        request(options) {
            val response = dm.contacts.sync()
            onSync.postValue(response)
        }
    }
}
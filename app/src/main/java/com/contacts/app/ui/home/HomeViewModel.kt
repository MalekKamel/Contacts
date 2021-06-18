package com.contacts.app.ui.home

import androidx.lifecycle.MutableLiveData
import app.common.data.DataManager
import app.common.data.model.ContactItem
import app.common.presentation.ui.vm.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Sha on 7/28/20.
 */

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

class HomeViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {
    val contacts = MutableLiveData<List<ContactItem>>()
    val onSync = MutableLiveData<Boolean>()

    fun loadContacts() {
        request {
            val response = dm.contactsRepo.contacts()
            contacts.postValue(response)
        }
    }

    fun syncContacts() {
        request {
            val response = dm.contactsRepo.sync()
            onSync.postValue(response)
        }
    }
}
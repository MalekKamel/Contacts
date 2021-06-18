package com.contacts.app.ui.home

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import app.common.data.DataManager
import app.common.data.model.ContactItem
import app.common.data.model.ContactsRequest
import app.common.presentation.ui.paging.Pager
import app.common.presentation.ui.vm.BaseViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Sha on 7/28/20.
 */

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

class HomeViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    fun loadContactsPaged(request: ContactsRequest): LiveData<PagedList<ContactItem>> {
        return Pager.pageKeyed<Int, ContactItem> {
            loadInitial = { param ->
                // You may find it weird to pass a fake static key here!
                // But you should know that the first key must be NULL in the first query in Shopify!!
                // for this reason we depend on next and set it NULL for the first time
                loadContacts(request) {
                    param.callback.onResult(it, 1, 1)
                }
            }
            loadAfter = { param ->
                loadContacts(request.apply { nextPage = param.key }) {
                    param.callback.onResult(it, param.key + 1)
                }
            }
        }
    }

    private fun loadContacts(request: ContactsRequest, onLoad: (List<ContactItem>) -> Unit) {
        request {
            val response = dm.contactsRepo.contacts(request)
            onLoad(response)
        }
    }
}
package com.contacts.app.home

import app.common.data.domain.contacts.ContactsRepoInterface
import app.common.data.model.ContactItem

class FakeContactsRepo : ContactsRepoInterface {
    override suspend fun contacts(): List<ContactItem> {
        return listOf(ContactItem(1, "name", "123"))
    }

    override suspend fun sync(): Boolean {
        return true
    }
}
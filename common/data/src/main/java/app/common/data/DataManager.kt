package app.common.data

import app.common.data.domain.contacts.ContactsRepoInterface

open class DataManager(
    val contactsRepo: ContactsRepoInterface
)

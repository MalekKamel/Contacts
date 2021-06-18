package app.common.data.domain.contacts

import app.common.data.model.ContactItem


class ContactsProviderDataSrc(private val contactsRetriever: ContactsRetriever) {

    suspend fun all(): List<ContactItem> {
        return contactsRetriever.retrieve()
    }
}
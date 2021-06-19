package app.common.data.domain.contacts

import app.common.data.model.ContactItem

interface ContactsProviderDataSrcContract {
    suspend fun all(): List<ContactItem>
}

class ContactsProviderDataSrc(private val contactsRetriever: ContactsRetriever) :
    ContactsProviderDataSrcContract {

    override suspend fun all(): List<ContactItem> {
        return contactsRetriever.retrieve()
    }
}
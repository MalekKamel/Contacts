package app.common.data.domain.contacts

import app.common.data.model.ContactItem
import app.common.data.model.ContactsRequest

class ContactsRepo(
    private val remoteSrc: ContactsRemoteDataSrc,
    private val localSrc: ContactsLocalDataSrc,
    private val providerDataSrc: ContactsProviderDataSrc,
) {

    suspend fun contacts(request: ContactsRequest): List<ContactItem> {
        val items = providerDataSrc.all()
        save(items)

        return items
    }

    suspend fun save(contacts: List<ContactItem>) {
        deleteAll()
        localSrc.save(contacts)
    }

    suspend fun deleteAll() {
        localSrc.deleteAll()
    }

}

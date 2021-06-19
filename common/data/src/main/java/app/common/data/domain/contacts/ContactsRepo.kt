package app.common.data.domain.contacts

import app.common.data.model.ContactItem

interface ContactsRepoInterface {
    suspend fun contacts(): List<ContactItem>
    suspend fun sync(): Boolean
}

class ContactsRepo(
    private val localSrc: ContactsLocalDataSrc,
    private val providerDataSrc: ContactsProviderDataSrc,
    private val contactSynchronizer: ContactSynchronizer,
) : ContactsRepoInterface {

    override suspend fun contacts(): List<ContactItem> {
        val items = localSrc.all()
        if (items.isNotEmpty()) return items
        return fetchFromProvider()
    }

    override suspend fun sync(): Boolean {
        val result = contactSynchronizer.sync()
        save(result.new)
        update(result.modified)
        delete(result.deleted)
        return result.isModified()
    }

    private suspend fun fetchFromProvider(): List<ContactItem> {
        val providerItems = providerDataSrc.all()
        save(providerItems)
        return providerItems
    }

    private suspend fun save(contacts: List<ContactItem>) {
        localSrc.save(contacts)
    }

    private suspend fun update(contacts: List<ContactItem>) {
        localSrc.update(contacts)
    }

    private suspend fun delete(contacts: List<ContactItem>) {
        localSrc.delete(contacts)
    }

}

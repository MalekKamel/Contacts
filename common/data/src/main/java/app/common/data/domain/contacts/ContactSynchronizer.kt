package app.common.data.domain.contacts

import app.common.data.model.ContactItem
import app.common.data.model.ContactSyncResult

interface ContactSynchronizerContract {
    suspend fun sync(): ContactSyncResult
}

class ContactSynchronizer(
    private val providerDataSrc: ContactsProviderDataSrc,
    private val localSrc: ContactsLocalDataSrc,
) : ContactSynchronizerContract {

    override suspend fun sync(): ContactSyncResult {
        val originalItems = providerDataSrc.all()
        val localItems = localSrc.all()

        val originalItemsMap = hashMapOf<Long, ContactItem>()
        val localItemsMap = hashMapOf<Long, ContactItem>()

        originalItems.forEach { originalItemsMap[it.id] = it }
        localItems.forEach { localItemsMap[it.id] = it }

        val newItems = mutableListOf<ContactItem>()
        val modifiedItems = mutableListOf<ContactItem>()
        val deletedItems = mutableListOf<ContactItem>()

        for (originalItem in originalItems) {
            val localItem: ContactItem? = localItemsMap[originalItem.id]

            // The item is not saved locally, it's a new one
            if (localItem == null) {
                newItems.add(originalItem)
                continue
            }

            // The item is not modified, ignore it.
            if (localItem.hashCode() == originalItem.hashCode()) continue

            // It's a modified item
            modifiedItems.add(originalItem)
        }

        // find deleted items
        for (localItem in localItems) {
            val originalItem: ContactItem? = originalItemsMap[localItem.id]
            if (originalItem != null) continue
            deletedItems.add(localItem)
        }

        return ContactSyncResult(newItems, modifiedItems, deletedItems)
    }

}
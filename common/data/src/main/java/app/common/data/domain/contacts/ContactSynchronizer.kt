package app.common.data.domain.contacts

import app.common.data.model.ContactItem
import app.common.data.model.ContactSyncResult

class ContactSynchronizer(
    private val providerDataSrc: ContactsProviderDataSrc,
    private val localSrc: ContactsLocalDataSrc,
) {

    suspend fun sync(): ContactSyncResult {
        val originalItems = providerDataSrc.all()
        val localItems = localSrc.all()

        val newItems = mutableListOf<ContactItem>()
        val modifiedItems = mutableListOf<ContactItem>()

        for (originalItem in originalItems) {
            val localItem: ContactItem? = localItems.firstOrNull { it.id == originalItem.id }

            // The item is not saved locally, it's a new one
            if (localItem == null) {
                newItems.add(originalItem)
                continue
            }

            // The item is not modified, ignore it.
            if (localItem.hashCode() == originalItem.hashCode()) continue

            modifiedItems.add(originalItem)
        }
        return ContactSyncResult(newItems, modifiedItems)
    }

}
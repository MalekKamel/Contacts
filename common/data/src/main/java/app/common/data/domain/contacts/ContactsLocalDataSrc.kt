package app.common.data.domain.contacts

import com.sha.modelmapper.ListMapper
import app.common.data.db.ContactsDatabase
import app.common.data.model.*

class ContactsLocalDataSrc(private val db: ContactsDatabase) {

    suspend fun all(): List<ContactItem> {
        return db.contactDao().all().map { ContactItemMapper().map(it) }
    }

    suspend fun save(contacts: List<ContactItem>) {
        db.contactDao().insert(ListMapper(ContactEntityMapper()).map(contacts))
    }

    suspend fun deleteAll() {
        db.contactDao().deleteAll()
    }

}
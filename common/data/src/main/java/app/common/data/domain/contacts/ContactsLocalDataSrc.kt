package app.common.data.domain.contacts

import app.common.data.db.ContactsDatabase
import app.common.data.model.ContactEntityMapper
import app.common.data.model.ContactItem
import app.common.data.model.ContactItemMapper
import com.sha.modelmapper.ListMapper

class ContactsLocalDataSrc(private val db: ContactsDatabase) {

    suspend fun all(): List<ContactItem> {
        return db.contactDao().all().map { ContactItemMapper().map(it) }
    }

    suspend fun save(contacts: List<ContactItem>) {
        db.contactDao().insert(ListMapper(ContactEntityMapper()).map(contacts))
    }

    suspend fun update(contacts: List<ContactItem>) {
        db.contactDao().update(ListMapper(ContactEntityMapper()).map(contacts))
    }

    suspend fun deleteAll() {
        db.contactDao().deleteAll()
    }

}
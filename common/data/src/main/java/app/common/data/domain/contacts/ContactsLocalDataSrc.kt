package app.common.data.domain.contacts

import app.common.data.db.ContactsDatabase
import app.common.data.model.ContactEntityMapper
import app.common.data.model.ContactItem
import app.common.data.model.ContactItemMapper
import com.sha.modelmapper.ListMapper

interface ContactsLocalDataSrcContract {
    suspend fun all(): List<ContactItem>
    suspend fun save(contacts: List<ContactItem>)
    suspend fun update(contacts: List<ContactItem>)
    suspend fun delete(contacts: List<ContactItem>)
}

class ContactsLocalDataSrc(private val db: ContactsDatabase) : ContactsLocalDataSrcContract {

    override suspend fun all(): List<ContactItem> {
        return db.contactDao().all().map { ContactItemMapper().map(it) }
    }

    override suspend fun save(contacts: List<ContactItem>) {
        db.contactDao().insert(ListMapper(ContactEntityMapper()).map(contacts))
    }

    override suspend fun update(contacts: List<ContactItem>) {
        db.contactDao().update(ListMapper(ContactEntityMapper()).map(contacts))
    }

    override suspend fun delete(contacts: List<ContactItem>) {
        db.contactDao().delete(ListMapper(ContactEntityMapper()).map(contacts))
    }

}
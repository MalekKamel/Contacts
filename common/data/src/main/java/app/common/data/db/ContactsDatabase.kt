package app.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.common.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
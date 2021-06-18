package app.common.data.db

import androidx.room.*
import app.common.data.model.ContactEntity

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    suspend fun all(): List<ContactEntity>

    @Insert
    suspend fun insert(contact: ContactEntity)

    @Insert
    suspend fun insert(contacts: List<ContactEntity>)

    @Update
    suspend fun update(contacts: List<ContactEntity>)

    @Delete
    suspend fun delete(contact: ContactEntity)

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()
}
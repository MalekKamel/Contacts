package app.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sha.modelmapper.Mapper

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    var id: Long,
    var name: String?,
    var phone: String?
)

class ContactEntityMapper : Mapper<ContactItem, ContactEntity> {
    override fun map(input: ContactItem): ContactEntity {
        return ContactEntity(
            id = input.id,
            name = input.name,
            phone = input.phone
        )
    }
}

class ContactItemMapper : Mapper<ContactEntity, ContactItem> {
    override fun map(input: ContactEntity): ContactItem {
        return ContactItem(
            id = input.id,
            name = input.name,
            phone = input.phone
        )
    }
}
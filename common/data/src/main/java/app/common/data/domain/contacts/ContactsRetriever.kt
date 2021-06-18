package app.common.data.domain.contacts

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import app.common.data.extension.int
import app.common.data.extension.string
import app.common.data.model.ContactItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactsRetriever(private val contentResolver: ContentResolver) {

    suspend fun retrieve(): List<ContactItem> {
        return suspendCoroutine { cont ->
            val items: MutableList<ContactItem> = ArrayList()

            val rawContactsIdList = rawContactsIdList
            val contactListSize = rawContactsIdList.size

            for (i in 0 until contactListSize) {
                val contactItem = queryContact(i) ?: continue
                items.add(contactItem)
            }

            cont.resume(items)
        }
    }

    private fun queryContact(index: Int): ContactItem? {
        val rawContactId = rawContactsIdList[index]

        val whereClauseBuf = StringBuffer()
        whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID)
        whereClauseBuf.append("=")
        whereClauseBuf.append(rawContactId)

        val cursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            whereClauseBuf.toString(),
            null,
            null
        )

        if (cursor == null || cursor.count == 0) {
            return null
        }

        cursor.moveToFirst()

        val contactId =
            cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))

        val contactItem = ContactItem()
        contactItem.id = contactId

        do {
            val mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE))
            getColumnValueByMimetype(cursor, mimeType, contactItem)
        } while (cursor.moveToNext())

        return contactItem
    }

    private val projection: Array<String> by lazy {
        val queryColumnList: MutableList<String> = ArrayList()
        queryColumnList.add(ContactsContract.Data.CONTACT_ID)
        queryColumnList.add(ContactsContract.Data.MIMETYPE)
        queryColumnList.add(ContactsContract.Data.DATA1)
        queryColumnList.add(ContactsContract.Data.DATA2)
        queryColumnList.add(ContactsContract.Data.DATA3)
        queryColumnList.add(ContactsContract.Data.DATA4)
        queryColumnList.add(ContactsContract.Data.DATA5)
        queryColumnList.add(ContactsContract.Data.DATA6)
        queryColumnList.add(ContactsContract.Data.DATA7)
        queryColumnList.add(ContactsContract.Data.DATA8)
        queryColumnList.add(ContactsContract.Data.DATA9)
        queryColumnList.add(ContactsContract.Data.DATA10)
        queryColumnList.add(ContactsContract.Data.DATA11)
        queryColumnList.add(ContactsContract.Data.DATA12)
        queryColumnList.add(ContactsContract.Data.DATA13)
        queryColumnList.add(ContactsContract.Data.DATA14)
        queryColumnList.add(ContactsContract.Data.DATA15)
        queryColumnList.toTypedArray()
    }

    private val rawContactsIdList: List<Int>
        get() {
            val items: MutableList<Int> = ArrayList()
            val rawContactUri: Uri = ContactsContract.RawContacts.CONTENT_URI
            val queryColumnArr = arrayOf(ContactsContract.RawContacts._ID)
            val cursor: Cursor =
                contentResolver.query(
                    rawContactUri,
                    queryColumnArr,
                    null,
                    null,
                    null
                )
                    ?: return emptyList()
            cursor.moveToFirst()
            do {
                val idColumnIndex: Int = cursor.getColumnIndex(ContactsContract.RawContacts._ID)
                val rawContactsId: Int = cursor.getInt(idColumnIndex)
                items.add(rawContactsId)
            } while (cursor.moveToNext())
            cursor.close()
            return items
        }

    private fun getColumnValueByMimetype(
        cursor: Cursor,
        mimeType: String,
        contactItem: ContactItem
    ) {
        when (mimeType) {
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                val phoneNumber: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val phoneTypeInt: Int =
                    cursor.int(ContactsContract.CommonDataKinds.Phone.TYPE)
                val phoneTypeStr = getPhoneTypeString(phoneTypeInt)
                contactItem.phone = phoneNumber
            }
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE -> {
                val displayName: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME)
                val givenName: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)
                val familyName: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)
                contactItem.name = displayName
            }
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                val emailAddress: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Email.ADDRESS)
                val emailType: Int =
                    cursor.int(ContactsContract.CommonDataKinds.Email.TYPE)
                val emailTypeStr = getEmailTypeString(emailType)
            }
            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE -> {
                val imProtocol: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Im.PROTOCOL)
                val imId: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Im.DATA)
            }
            ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE -> {
                val nickName: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Nickname.NAME)
            }
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE -> {
                val company: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Organization.COMPANY)
                val department: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Organization.DEPARTMENT)
                val title: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Organization.TITLE)
                val jobDescription: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION)
                val officeLocation: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Organization.OFFICE_LOCATION)
            }
            ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE -> {
                val address: String? =
                    cursor.string(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS)
                val addressTypeInt: Int =
                    cursor.int(ContactsContract.CommonDataKinds.SipAddress.TYPE)
                val addressTypeStr = getEmailTypeString(addressTypeInt)
            }
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE -> {
                val country: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)
                val city: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredPostal.CITY)
                val region: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredPostal.REGION)
                val street: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredPostal.STREET)
                val postcode: String? =
                    cursor.string(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)
                val postType: Int =
                    cursor.int(ContactsContract.CommonDataKinds.StructuredPostal.TYPE)
                val postTypeStr = getEmailTypeString(postType)
            }
            ContactsContract.CommonDataKinds.Identity.CONTENT_ITEM_TYPE -> {
                val identity: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Identity.IDENTITY)
                val namespace: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Identity.NAMESPACE)
            }
            ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE -> {
                val photo: ByteArray? =
                    cursor.getBlob(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO))
                val photoFileId: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID)
            }
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE -> {
                val groupId: Int =
                    cursor.int(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID)
            }
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE -> {
                val websiteUrl: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Website.URL)
                val websiteTypeInt: Int =
                    cursor.int(ContactsContract.CommonDataKinds.Website.TYPE)
                val websiteTypeStr = getEmailTypeString(websiteTypeInt)
            }
            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE -> {
                val note: String? =
                    cursor.string(ContactsContract.CommonDataKinds.Note.NOTE)
            }
        }
    }

    private fun getEmailTypeString(dataType: Int): String {
        return when (dataType) {
            ContactsContract.CommonDataKinds.Email.TYPE_HOME -> "Home"
            ContactsContract.CommonDataKinds.Email.TYPE_WORK -> "Work"
            else -> ""
        }
    }

    private fun getPhoneTypeString(dataType: Int): String {
        return when (dataType) {
            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> "Home"
            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> "Work"
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> "Mobile"
            else -> ""
        }
    }
}

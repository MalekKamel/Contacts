package app.common.data.domain.contacts

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import app.common.data.model.ContactItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactsRetriever(private val contentResolver: ContentResolver) {

    suspend fun retrieve(): List<ContactItem> {
        return suspendCoroutine { cont ->
            val items: MutableList<ContactItem> = ArrayList()

            // Get all raw contacts id list.
            val rawContactsIdList = rawContactsIdList
            val contactListSize = rawContactsIdList.size

            // Loop in the raw contacts list.
            for (i in 0 until contactListSize) {
                // Get the raw contact id.
                val rawContactId = rawContactsIdList[i]

                // Build query condition string. Query rows by contact id.
                val whereClauseBuf = StringBuffer()
                whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID)
                whereClauseBuf.append("=")
                whereClauseBuf.append(rawContactId)

                // Query data table and return related contact data.
                val cursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    queryColumnList,
                    whereClauseBuf.toString(),
                    null,
                    null
                )

                if (cursor == null || cursor.count == 0) {
                    cont.resume(items)
                    return@suspendCoroutine
                }

                cursor.moveToFirst()


                val contactId =
                    cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))

                val contactItem = ContactItem()
                contactItem.id = contactId

                do {
                    val mimeType =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE))
                    getColumnValueByMimetype(cursor, mimeType, contactItem)
                } while (cursor.moveToNext())

                items.add(contactItem)
            }

            cont.resume(items)
        }
    }

    private val queryColumnList: Array<String> by lazy {
        val queryColumnList: MutableList<String> = ArrayList()

        // ContactsContract.Data.CONTACT_ID = "contact_id";
        queryColumnList.add(ContactsContract.Data.CONTACT_ID)

        // ContactsContract.Data.MIMETYPE = "mimetype";
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

        // Translate column name list to array.
        queryColumnList.toTypedArray()
    }

    private val rawContactsIdList: List<Int>
        get() {
            val items: MutableList<Int> = ArrayList()

            // Row contacts content uri( access raw_contacts table. ).
            val rawContactUri: Uri = ContactsContract.RawContacts.CONTENT_URI
            // Return _id column in contacts raw_contacts table.
            val queryColumnArr = arrayOf(ContactsContract.RawContacts._ID)
            // Query raw_contacts table and return raw_contacts table _id.
            val cursor: Cursor =
                contentResolver.query(rawContactUri,
                    queryColumnArr,
                    null,
                    null,
                    null)
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
                // Phone.NUMBER == data1
                val phoneNumber: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                // Phone.TYPE == data2
                val phoneTypeInt: Int =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                val phoneTypeStr = getPhoneTypeString(phoneTypeInt)
                contactItem.phone = phoneNumber
            }
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE -> {
                // StructuredName.DISPLAY_NAME == data1
                val displayName: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME))
                // StructuredName.GIVEN_NAME == data2
                val givenName: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))
                // StructuredName.FAMILY_NAME == data3
                val familyName: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))
                contactItem.name = displayName
            }
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                // Email.ADDRESS == data1
                val emailAddress: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))
                // Email.TYPE == data2
                val emailType: Int =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))
                val emailTypeStr = getEmailTypeString(emailType)
            }
            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE -> {
                // Im.PROTOCOL == data5
                val imProtocol: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL))
                // Im.DATA == data1
                val imId: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA))
            }
            ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE -> {
                // Nickname.NAME == data1
                val nickName: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME))
            }
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE -> {
                // Organization.COMPANY == data1
                val company: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
                // Organization.DEPARTMENT == data5
                val department: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT))
                // Organization.TITLE == data4
                val title: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))
                // Organization.JOB_DESCRIPTION == data6
                val jobDescription: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION))
                // Organization.OFFICE_LOCATION == data9
                val officeLocation: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.OFFICE_LOCATION))
            }
            ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE -> {
                // SipAddress.SIP_ADDRESS == data1
                val address: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS))
                // SipAddress.TYPE == data2
                val addressTypeInt: Int =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.TYPE))
                val addressTypeStr = getEmailTypeString(addressTypeInt)
            }
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE -> {
                // StructuredPostal.COUNTRY == data10
                val country: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))
                // StructuredPostal.CITY == data7
                val city: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))
                // StructuredPostal.REGION == data8
                val region: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))
                // StructuredPostal.STREET == data4
                val street: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))
                // StructuredPostal.POSTCODE == data9
                val postcode: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))
                // StructuredPostal.TYPE == data2
                val postType: Int =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE))
                val postTypeStr = getEmailTypeString(postType)
            }
            ContactsContract.CommonDataKinds.Identity.CONTENT_ITEM_TYPE -> {
                // Identity.IDENTITY == data1
                val identity: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.IDENTITY))
                // Identity.NAMESPACE == data2
                val namespace: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.NAMESPACE))
            }
            ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE -> {
                // Photo.PHOTO == data15
                val photo: ByteArray? =
                    cursor.getBlob(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO))
                // Photo.PHOTO_FILE_ID == data14
                val photoFileId: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID))
            }
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE -> {
                // GroupMembership.GROUP_ROW_ID == data1
                val groupId: Int =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID))
            }
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE -> {
                // Website.URL == data1
                val websiteUrl: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL))
                // Website.TYPE == data2
                val websiteTypeInt: Int =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE))
                val websiteTypeStr = getEmailTypeString(websiteTypeInt)
            }
            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE -> {
                // Note.NOTE == data1
                val note: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))
            }
        }
    }

    private fun getEmailTypeString(dataType: Int): String {
        var ret = ""
        if (ContactsContract.CommonDataKinds.Email.TYPE_HOME == dataType) {
            ret = "Home"
        } else if (ContactsContract.CommonDataKinds.Email.TYPE_WORK == dataType) {
            ret = "Work"
        }
        return ret
    }

    private fun getPhoneTypeString(dataType: Int): String {
        var ret = ""
        if (ContactsContract.CommonDataKinds.Phone.TYPE_HOME == dataType) {
            ret = "Home"
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_WORK == dataType) {
            ret = "Work"
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE == dataType) {
            ret = "Mobile"
        }
        return ret
    }
}
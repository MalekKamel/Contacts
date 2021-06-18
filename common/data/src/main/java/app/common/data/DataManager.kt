package app.common.data

import app.common.data.domain.contacts.ContactsRepo
import app.common.data.pref.SharedPref

open class DataManager(
        val pref: SharedPref,
        val contactsRepo: ContactsRepo
)

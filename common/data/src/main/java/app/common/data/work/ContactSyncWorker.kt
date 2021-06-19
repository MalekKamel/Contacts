package app.common.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.common.data.domain.contacts.ContactsRepo
import app.common.data.domain.contacts.ContactsRepoInterface
import org.koin.java.KoinJavaComponent.inject

class ContactSyncWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val contactsRepo: ContactsRepoInterface by inject(ContactsRepo::class.java)

    override suspend fun doWork(): Result {
        syncContacts()
        return Result.success()
    }

    private suspend fun syncContacts() {
        contactsRepo.sync()
    }

}
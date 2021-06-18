package app.common.data.work

import androidx.work.*
import app.common.core.app.CoreApp
import java.util.concurrent.TimeUnit

object AppWorkManager {

    fun startContactsWorker() {
        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
        val request = PeriodicWorkRequest.Builder(
            ContactSyncWorker::class.java,
            TimeUnit.MINUTES.toMillis(15),
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(CoreApp.context)
            .enqueueUniquePeriodicWork(
                ContactSyncWorker::class.java.name,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }

}
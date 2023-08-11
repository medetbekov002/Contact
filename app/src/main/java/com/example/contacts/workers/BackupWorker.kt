package com.example.contacts.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.contacts.enums.BackupType
import com.example.contacts.util.BackupHelper
import com.example.contacts.util.DeviceContactsHelper
import com.example.contacts.util.LocalContactsHelper
import com.example.contacts.util.Preferences
import java.util.concurrent.TimeUnit

class BackupWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val contactHelpers = when (Preferences.getBackupType()) {
            BackupType.BOTH -> listOf(
                DeviceContactsHelper(applicationContext),
                LocalContactsHelper(applicationContext)
            )
            BackupType.DEVICE -> listOf(DeviceContactsHelper(applicationContext))
            BackupType.LOCAL -> listOf(LocalContactsHelper(applicationContext))
            else -> return Result.success()
        }
        contactHelpers.forEach {
            BackupHelper.backup(applicationContext, it)
        }
        return Result.success()
    }

    companion object {
        private const val workerName = "BackupWorker"

        fun enqueue(context: Context, force: Boolean = false) {
            val delayInHours = Preferences.getString(Preferences.backupIntervalKey, "12")
                .orEmpty().toLong()
            val request = PeriodicWorkRequestBuilder<BackupWorker>(delayInHours, TimeUnit.HOURS)
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    workerName,
                    if (force) ExistingPeriodicWorkPolicy.UPDATE else ExistingPeriodicWorkPolicy.KEEP,
                    request.build()
                )
        }
    }
}

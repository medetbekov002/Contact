package com.example.contacts

import android.app.Application
import com.example.contacts.db.DatabaseHolder
import com.example.contacts.util.Preferences
import com.example.contacts.util.ShortcutHelper
import com.example.contacts.workers.BackupWorker

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ShortcutHelper.createShortcuts(this)

        DatabaseHolder.init(this)

        Preferences.init(this)

        BackupWorker.enqueue(this)
    }
}

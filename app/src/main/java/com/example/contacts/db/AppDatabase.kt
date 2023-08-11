package com.example.contacts.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.db.dao.LocalContactsDao
import com.example.contacts.db.obj.DbDataItem
import com.example.contacts.db.obj.LocalContact

@Database(
    entities = [LocalContact::class, DbDataItem::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localContactsDao(): LocalContactsDao
}

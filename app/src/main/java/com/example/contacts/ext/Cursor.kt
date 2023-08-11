package com.example.contacts.ext

import android.database.Cursor
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull

fun Cursor.stringValue(index: String): String? {
    return getStringOrNull(getColumnIndex(index))
}

fun Cursor.intValue(index: String): Int? {
    return getIntOrNull(getColumnIndex(index))
}

fun Cursor.longValue(index: String): Long? {
    return getLongOrNull(getColumnIndex(index))
}

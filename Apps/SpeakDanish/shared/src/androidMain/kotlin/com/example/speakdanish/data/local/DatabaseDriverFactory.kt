package com.example.speakdanish.data.local

import android.content.Context
import com.plcoding.speakdanish.database.RecordingDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RecordingDatabase.Schema, context, "recording_db")
    }
}
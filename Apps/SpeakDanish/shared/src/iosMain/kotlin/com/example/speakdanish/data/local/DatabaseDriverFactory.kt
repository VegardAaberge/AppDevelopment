package com.example.speakdanish.data.local

import com.plcoding.speakdanish.database.RecordingDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecordingDatabase.Schema, "note_db")
    }
}
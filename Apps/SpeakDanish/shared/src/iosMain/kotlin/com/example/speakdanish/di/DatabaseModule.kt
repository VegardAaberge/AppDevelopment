package com.example.speakdanish.di

import com.example.speakdanish.data.local.DatabaseDriverFactory
import com.example.speakdanish.data.SqlDelightRecordingDataSource
import com.example.speakdanish.domain.RecordingDataSource
import com.plcoding.speakdanish.database.RecordingDatabase

class DatabaseModule {
    private val factory by lazy { DatabaseDriverFactory() }
    val recordingDataSource: RecordingDataSource by lazy {
        SqlDelightRecordingDataSource(RecordingDatabase(factory.createDriver()))
    }
}
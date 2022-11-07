package com.example.speakdanish.data

import com.example.speakdanish.domain.Recording
import com.example.speakdanish.domain.RecordingDataSource
import com.example.speakdanish.domain.time.DateTimeUtil
import com.plcoding.speakdanish.database.RecordingDatabase

class SqlDelightRecordingDataSource(
    db: RecordingDatabase
) : RecordingDataSource {

    private val queries = db.recordingQueries

    override suspend fun insertRecording(recording: Recording) {
        queries.insertRecording(
            id = recording.id,
            text = recording.text,
            path = recording.path,
            created = DateTimeUtil.toEpochMilis(recording.created)
        )
    }

    override suspend fun getRecordingById(id: String): Recording? {
        return queries
            .getRecordingById(id)
            .executeAsOneOrNull()
            ?.toRecording()
    }

    override suspend fun getAllRecordings(): List<Recording> {
        return queries
            .getAllRecordings()
            .executeAsList()
            .map { it.toRecording() }
            .sortedByDescending { it.created }
    }

    override suspend fun deleteRecordingById(id: String) {
        queries.deleteRecordingById(id)
    }
}
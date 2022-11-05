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
            content = recording.content,
            created = DateTimeUtil.toEpochMilis(recording.created)
        )
    }

    override suspend fun getRecordingById(id: String): Recording? {
        return queries
            .getRecordingById(id)
            .executeAsOneOrNull()
            ?.toRecording()
    }

    override suspend fun deleteRecordingById(id: String) {
        queries.deleteRecordingById(id)
    }
}
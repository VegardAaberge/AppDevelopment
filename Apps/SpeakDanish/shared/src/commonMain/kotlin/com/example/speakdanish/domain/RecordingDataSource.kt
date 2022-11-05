package com.example.speakdanish.domain

interface RecordingDataSource {
    suspend fun insertRecording(recording: Recording)
    suspend fun getRecordingById(id: String) : Recording?
    suspend fun deleteRecordingById(id: String)
}
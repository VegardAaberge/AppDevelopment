package com.example.speakdanish.android.recordings

import android.view.MotionEvent

sealed class RecordingsScreenEvent {
    data class ListenToRecording(val recordId: String): RecordingsScreenEvent()
    data class DeleteRecording(val recordId: String): RecordingsScreenEvent()
    data class PlayRecording(val recordId: String, val fast: Boolean): RecordingsScreenEvent()
    data class NewRecording(val recordId: String): RecordingsScreenEvent()
}

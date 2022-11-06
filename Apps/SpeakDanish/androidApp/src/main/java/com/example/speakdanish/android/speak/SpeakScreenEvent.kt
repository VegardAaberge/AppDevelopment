package com.example.speakdanish.android.speak

import android.view.MotionEvent

sealed class SpeakScreenEvent {
    object ListenAgain: SpeakScreenEvent()
    data class RecordTapped(val saveDirectory: String, val motionEvent: MotionEvent): SpeakScreenEvent()
    object HistoryTapped: SpeakScreenEvent()
    object SubmitTapped : SpeakScreenEvent()
    data class ListenToRecording(val recordId: String): SpeakScreenEvent()
}

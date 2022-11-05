package com.example.speakdanish.android.speak

sealed class SpeakScreenEvent {
    object ListenAgain: SpeakScreenEvent()
    object RecordTapped: SpeakScreenEvent()
    object SettingsTapped: SpeakScreenEvent()
    object SubmitTapped : SpeakScreenEvent()
    data class ListenToRecording(val recordId: String): SpeakScreenEvent()
}

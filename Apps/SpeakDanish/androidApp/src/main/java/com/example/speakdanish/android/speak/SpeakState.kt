package com.example.speakdanish.android.speak

import android.os.Parcelable
import android.speech.tts.Voice
import com.example.speakdanish.domain.Recording
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SpeakState(
    val isRecording: Boolean = false,
    val recording: @RawValue Recording? = null,
    val sentence: String = "",
) : Parcelable
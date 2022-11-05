package com.example.speakdanish.android.speak

import android.os.Parcelable
import com.example.speakdanish.domain.Recording
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SpeakState(
    val recordings: @RawValue List<Recording> = emptyList(),
    val sentence: String = "",
) : Parcelable
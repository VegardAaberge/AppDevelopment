package com.example.speakdanish.android.recordings

import android.os.Parcelable
import com.example.speakdanish.domain.Recording
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class RecordingsState(
    val playRecordId: String? = null,
    val recordings: @RawValue List<Recording> = emptyList(),
) : Parcelable
package com.example.speakdanish.data

import com.example.speakdanish.domain.Recording
import database.RecordingEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun RecordingEntity.toRecording(): Recording {
    return Recording(
        id = id,
        content = content,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
    )
}
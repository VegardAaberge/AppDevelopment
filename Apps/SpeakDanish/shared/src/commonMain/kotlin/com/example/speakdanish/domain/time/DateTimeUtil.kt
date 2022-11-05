package com.example.speakdanish.domain.time

import kotlinx.datetime.*

object DateTimeUtil {

    fun now() : LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMilis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
}
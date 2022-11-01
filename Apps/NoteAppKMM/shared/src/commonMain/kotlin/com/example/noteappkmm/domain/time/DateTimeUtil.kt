package com.example.noteappkmm.domain.time

import kotlinx.datetime.*

object DateTimeUtil {

    fun now() : LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMilis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun formatNoteDate(dateTime: LocalDateTime) : String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = dateTime.dayOfMonth.let { day -> if(day < 10) "0$day" else day }
        val year = dateTime.year
        val hour = dateTime.hour.let { hour -> if(hour < 10) "0$hour" else hour }
        val minute = dateTime.minute.let { minute -> if(minute < 10) "0$minute" else minute }

        return "$month $day $year, $hour:$minute"
    }
}
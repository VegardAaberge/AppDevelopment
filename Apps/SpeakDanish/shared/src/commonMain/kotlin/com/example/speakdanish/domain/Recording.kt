package com.example.speakdanish.domain

import com.example.speakdanish.CommonParcelable
import com.example.speakdanish.CommonParcelize
import com.example.speakdanish.CommonTypeParceler
import com.example.speakdanish.LocalDateTimeParceler
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
@CommonParcelize
data class Recording(
    val id: String = randomUUID(),
    val content: String,

    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val created: LocalDateTime,
): CommonParcelable

package com.plcoding.ktorandroidchat.data.renote.dto

import com.plcoding.ktorandroidchat.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss"

@Serializable
data class MessageDto (
    val id: Long,
    val message: String,
    val profileName: String?,
    val created: String,
    val createdBy: String
){
    fun toMessage(): Message {
        return Message(
            text = message,
            formattedTime = created,
            username = createdBy,
        )
    }
}
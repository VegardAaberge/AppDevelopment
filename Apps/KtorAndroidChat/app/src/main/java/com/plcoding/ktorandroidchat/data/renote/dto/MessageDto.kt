package com.plcoding.ktorandroidchat.data.renote.dto

import com.plcoding.ktorandroidchat.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*

@Serializable
data class MessageDto (
    val text: String,
    val timestamp: Long,
    val username: String,
    val id: Long
){
    fun toMessage(): Message {
        val date = Date(timestamp)
        val formatedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            text = text,
            formattedTime = formatedDate,
            username = username,
        )
    }
}
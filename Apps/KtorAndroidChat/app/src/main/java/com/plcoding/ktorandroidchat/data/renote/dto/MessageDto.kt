package com.plcoding.ktorandroidchat.data.renote.dto

import com.plcoding.ktorandroidchat.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*

@Serializable
data class MessageDto (
    val id: Long,
    val message: String,
    val profileName: String?,
    val created: Long,
    val createdBy: String
){
    fun toMessage(): Message {
        val date = Date(created)
        val formatedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            text = message,
            formattedTime = formatedDate,
            username = createdBy,
        )
    }
}
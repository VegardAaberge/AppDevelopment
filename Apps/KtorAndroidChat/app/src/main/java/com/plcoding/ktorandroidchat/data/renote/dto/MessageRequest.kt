package com.plcoding.ktorandroidchat.data.renote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageRequest (
    val message: String
)
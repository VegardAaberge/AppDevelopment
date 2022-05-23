package com.plcoding.ktorandroidchat.data.renote

import com.plcoding.ktorandroidchat.domain.model.Message
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class MessageServiceImpl(
    private val myApi: MyApi
) : MessageService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            val messageResponse = myApi.getMessages()
            return messageResponse.map { it.toMessage() }
        }catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
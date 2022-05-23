package com.plcoding.ktorandroidchat.data.renote

import com.plcoding.ktorandroidchat.data.renote.dto.MessageDto
import com.plcoding.ktorandroidchat.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*
import java.lang.Exception

class MessageServiceImpl(
    private val client: HttpClient
) : MessageService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDto>>(MessageService.Endpoints.GetAllMessages.url)
                .map { it.toMessage() }
        }catch (e: Exception) {
            emptyList()
        }
    }
}
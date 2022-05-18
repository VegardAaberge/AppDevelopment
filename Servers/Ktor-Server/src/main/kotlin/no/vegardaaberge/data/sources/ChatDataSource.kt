package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.Message

interface ChatDataSource {

    suspend fun insertMessage(message: Message) : Boolean

    suspend fun getAllMessages(username: String): List<Message>
}
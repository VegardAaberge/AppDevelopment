package no.vegardaaberge.data

import no.vegardaaberge.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages() : List<Message>

    suspend fun  insertMessage(message: Message)
}
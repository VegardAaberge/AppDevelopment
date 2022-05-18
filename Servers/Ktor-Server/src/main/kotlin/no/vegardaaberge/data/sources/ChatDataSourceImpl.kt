package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.Message
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.or

class ChatDataSourceImpl(
    private val db: CoroutineDatabase
) : ChatDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(username: String): List<Message> {
        return messages
            .find(or(Message::owner eq username, Message::receivers contains username))
            .descendingSort(Message::created)
            .toList()
    }

    override suspend fun insertMessage(message: Message) : Boolean {
        return messages
            .insertOne(message)
            .wasAcknowledged()
    }
}
package no.vegardaaberge.controllers

import no.vegardaaberge.data.model.Message
import no.vegardaaberge.data.responses.SimpleResponse
import no.vegardaaberge.data.sources.AuthDataSource
import no.vegardaaberge.data.sources.ChatDataSource

class ChatController(
    private val chatDataSource: ChatDataSource,
    private val authDataSource: AuthDataSource
) {

    suspend fun sendMessage(
        username: String,
        message: String,
        receivers: List<String>
    ) : SimpleResponse
    {
        val validUsers = mutableListOf<String>()
        val invalidUsers = mutableListOf<String>()

        for (receiver in receivers)
        {
            if(authDataSource.checkIfUserExist(receiver) && receiver != username)
                validUsers.add(receiver)
            else
                invalidUsers.add(receiver)
        }

        if(validUsers.isNotEmpty()){
            val messageEntity = Message(
                message = message,
                owner = username,
                receivers = validUsers,
                created = System.currentTimeMillis()
            )

            val isSuccessful = chatDataSource.insertMessage(messageEntity)

            return SimpleResponse(
                successful = isSuccessful,
                message = if(isSuccessful && invalidUsers.isEmpty()) {
                    "Message has been sent to ${validUsers.joinToString(", ")}"
                }else if (isSuccessful) {
                    "${invalidUsers.joinToString(", ")} are not valid receivers, sent the message to ${validUsers.joinToString(", ")}"
                }else {
                    "Failed to send message"
                }
            )
        }else{
            return SimpleResponse(
                successful = false,
                message = "Recipients does not exist"
            )
        }
    }

    suspend fun getAllMessages(username: String): List<Message> {
        return chatDataSource.getAllMessages(username)
    }
}
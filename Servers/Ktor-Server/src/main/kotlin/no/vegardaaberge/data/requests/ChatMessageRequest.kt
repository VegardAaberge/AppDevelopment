package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageRequest (
    val message: String,
    val receivers: List<String>
)

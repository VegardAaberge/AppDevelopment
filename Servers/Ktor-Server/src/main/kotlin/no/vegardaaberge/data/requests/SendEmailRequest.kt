package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class SendEmailRequest (
    val email: String
)
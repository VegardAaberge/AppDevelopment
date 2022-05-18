package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val username: String,
    val password: String,
    val email: String
)
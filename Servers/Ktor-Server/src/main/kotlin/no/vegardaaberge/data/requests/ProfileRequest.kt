package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class ProfileRequest (
    val profileName: String,
    val phoneNumber: String,
    val email: String,
    val newPassword: String,
)
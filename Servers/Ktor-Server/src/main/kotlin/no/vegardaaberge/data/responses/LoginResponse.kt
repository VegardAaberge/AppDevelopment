package no.vegardaaberge.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val successful: Boolean,
    val message: String,
    var email: String = "",
    var profileName: String = "",
    var phoneNumber: String = "",
    val createProfile: Boolean = false
)
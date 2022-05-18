package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeRequest (
    val code: String
)
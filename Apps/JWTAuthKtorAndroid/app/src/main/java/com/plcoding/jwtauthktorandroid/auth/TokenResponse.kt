package com.plcoding.jwtauthktorandroid.auth

data class TokenResponse (
    val access_token: String,
    val refresh_token: String,
    val profile_created: Boolean
)
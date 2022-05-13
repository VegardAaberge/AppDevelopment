package com.plcoding.jwtauthktorandroid.auth

interface AuthRepository {
    suspend fun signup(username: String, password: String): AuthResult<Unit>
    suspend fun signin(username: String, password: String): AuthResult<Unit>
}
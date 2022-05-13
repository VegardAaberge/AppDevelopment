package com.plcoding.jwtauthktorandroid.auth

import retrofit2.http.*

interface AuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @FormUrlEncoded
    @POST("api/v1/login")
    suspend fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}
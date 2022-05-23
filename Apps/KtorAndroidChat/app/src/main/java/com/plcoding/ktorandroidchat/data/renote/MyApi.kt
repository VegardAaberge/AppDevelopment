package com.plcoding.ktorandroidchat.data.renote

import com.plcoding.ktorandroidchat.data.renote.dto.MessageResponse
import retrofit2.http.GET

interface MyApi {

    @GET("/messages")
    suspend fun getMessages(): MessageResponse
}
package com.plcoding.ktorandroidchat.di

import com.plcoding.ktorandroidchat.data.renote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Singleton
    @Provides
    fun provideMyPlaygroupApi() : MyApi {
        val client = OkHttpClient
            .Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(MessageService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageService(client: MyApi): MessageService {
        return MessageServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideChatSocketService(client: HttpClient): ChatSocketService {
        return ChatSocketServiceImpl(client)
    }
}
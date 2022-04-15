package com.androiddevs.ktornoteapp.repositories

import android.content.Context
import com.androiddevs.ktornoteapp.data.local.NoteDao
import com.androiddevs.ktornoteapp.data.remote.NoteApi
import com.androiddevs.ktornoteapp.data.remote.requests.AccountRequest
import com.androiddevs.ktornoteapp.other.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    @ApplicationContext private val context: Context
) {
    suspend fun register(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            Authenticate(email, password)
        }catch (e: Exception) {
            Resource.error("Couldn't connect to the service, check your internet connection", null)
        }
    }

    suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            Authenticate(email, password)
        }catch (e: Exception) {
            Resource.error("Couldn't connect to the service, check your internet connection", null)
        }
    }

    private suspend fun Authenticate(email: String, password: String) : Resource<String> {
        val response = noteApi.login(AccountRequest(email, password))
        if(response.isSuccessful && response.body()!!.successful){
            return Resource.success(response.body()?.message)
        }else {
            return Resource.error(response.body()?.message ?: response.message(), null)
        }
    }
}
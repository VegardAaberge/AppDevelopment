package com.plcoding.jwtauthktorandroid.auth

import android.content.SharedPreferences
import android.util.Log
import retrofit2.HttpException
import java.lang.Exception

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
): AuthRepository {
    override suspend fun signup(username: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            signin(username, password)
        }catch (e: HttpException){
            if(e.code() == 401){
                AuthResult.Unauthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun signin(username: String, password: String): AuthResult<Unit> {
        return try {
            Log.d("JWTAPP", "Before request")
            val response = api.signIn(
                username = username,
                password = password
            )
            Log.d("JWTAPP", response.toString())
            prefs.edit()
                .putString("jwt", response.access_token)
                .apply()
            AuthResult.Authorized()
        }catch (e: HttpException){
            Log.d("JWTAPP", "HttpException ${e.toString()}")
            if(e.code() == 401){
                AuthResult.Unauthorized()
            }else{
                AuthResult.UnknownError();
            }
        }catch (e: Exception){
            Log.d("JWTAPP", "Unknown ${e.toString()}")
            AuthResult.UnknownError();
        }
    }
}
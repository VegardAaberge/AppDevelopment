package com.androiddevs.routes

import com.androiddevs.data.checkIfUserExists
import com.androiddevs.data.collections.User
import com.androiddevs.data.registerUser
import com.androiddevs.data.requests.AccountRequest
import com.androiddevs.data.responses.SimpleResponse
import com.androiddevs.security.getHashWithSalt
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(){
    route("/register"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExist = checkIfUserExists(request.email)
            if(!userExist){
                val newUser = User(request.email, getHashWithSalt(request.password))
                if(registerUser(newUser)){
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully created an account"))
                }else{
                    call.respond(HttpStatusCode.OK, SimpleResponse(false, "An unknown error occurred"))
                }
            }else{
                call.respond(HttpStatusCode.OK, SimpleResponse(false, "A user with that email already exist"))
            }
        }
    }
}
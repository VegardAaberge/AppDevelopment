package com.androiddevs

import com.androiddevs.data.checkPasswordForEnail
import com.androiddevs.data.collections.User
import com.androiddevs.data.registerUser
import com.androiddevs.routes.loginRoute
import com.androiddevs.routes.noteRoutes
import com.androiddevs.routes.registerRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)         // Extra info for all our responses
    install(CallLogging)            // Log all our http requests and the responses
    install(ContentNegotiation) {   // Make sure which type the server can respond with
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication){
        configureAuth()
    }
    install(Routing) {   // Can define URL endpoints. REST API
        registerRoute()
        loginRoute()
        noteRoutes()
    }
}

private fun Authentication.Configuration.configureAuth() {
    basic {
        realm = "Note Server"
        validate {credentials ->
            val email = credentials.name
            val password = credentials.password
            if(checkPasswordForEnail(email, password)) {
                UserIdPrincipal(email)
            }else null
        }
    }
}
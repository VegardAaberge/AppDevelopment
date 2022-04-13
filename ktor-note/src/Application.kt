package com.androiddevs

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)         // Extra info for all our responses
    install(CallLogging)            // Log all our http requests and the responses
    install(Routing)                // Can define URL endpoints. REST API
    install(ContentNegotiation) {   // Make sure which type the server can respond with
        gson {
            setPrettyPrinting()
        }
    }
}


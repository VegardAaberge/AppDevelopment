package no.vegardaaberge

import io.ktor.application.*
import no.vegardaaberge.plugins.*
import org.koin.ktor.ext.Koin
import no.vegardaaberge.di.mainModule

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureDI()
    configureSockets()
    configureSecurity()
    configureRouting()
    configureHTTP()
    configureSerialization()
    configureMonitoring()
}

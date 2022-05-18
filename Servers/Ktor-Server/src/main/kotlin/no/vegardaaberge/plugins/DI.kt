package no.vegardaaberge.plugins

import io.ktor.application.*
import no.vegardaaberge.di.mainModule
import org.koin.ktor.ext.Koin

fun Application.configureDI() {
    install(Koin){
        modules(mainModule)
    }
}

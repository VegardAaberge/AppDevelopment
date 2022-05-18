package no.vegardaaberge.plugins

import io.ktor.auth.*
import io.ktor.application.*
import no.vegardaaberge.controllers.AuthController
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val authController: AuthController by inject()

    install(Authentication){
        configureAuth(authController)
    }

    /*data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }*/
}

private fun Authentication.Configuration.configureAuth(authController: AuthController) {

    basic {
        realm = "MyPlaygroup Server"
        validate {credentials ->
            val username = credentials.name
            val password = credentials.password

            val isSuccessful = authController.tryLogin(username, password).successful
            if(isSuccessful) {
                UserIdPrincipal(username)
            }else null
        }
    }
}
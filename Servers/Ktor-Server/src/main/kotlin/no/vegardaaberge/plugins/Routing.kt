package no.vegardaaberge.plugins

import io.ktor.routing.*
import io.ktor.application.*
import no.vegardaaberge.controllers.AuthController
import no.vegardaaberge.controllers.ChatController
import no.vegardaaberge.routes.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val authController: AuthController by inject()
    val chatController: ChatController by inject()

    routing {
        login(authController)
        register(authController)
        registerProfile(authController)
        sendEmailRequest(authController)
        checkVerificationCode(authController)
        getChatMessages(chatController)
        sendChatMessage(chatController)
    }
}
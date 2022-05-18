package no.vegardaaberge.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import no.vegardaaberge.controllers.ChatController
import no.vegardaaberge.data.requests.ChatMessageRequest

fun Route.getChatMessages(chatController: ChatController) {
    authenticate {
        get("/getChatMessages") {

            val username = call.principal<UserIdPrincipal>()!!.name

            val messages = chatController.getAllMessages(username)

            call.respond(HttpStatusCode.OK, messages)
        }
    }
}

fun Route.sendChatMessage(chatController: ChatController) {
    authenticate {
        post("/sendChatMessage") {

            val username = call.principal<UserIdPrincipal>()!!.name
            val request = try {
                call.receive<ChatMessageRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
                return@post
            }

            val messageResponse = chatController.sendMessage(
                username = username,
                message = request.message,
                receivers = request.receivers
            )

            call.respond(HttpStatusCode.OK, messageResponse)
        }
    }
}
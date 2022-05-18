package no.vegardaaberge.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import no.vegardaaberge.controllers.AuthController
import no.vegardaaberge.data.requests.*

fun Route.register(authController: AuthController){
    post("/register") {
        val request = try {
            call.receive<RegisterRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
            return@post
        }

        val registerResponse = authController.tryRegister(request.username, request.password, request.email);

        call.respond(HttpStatusCode.OK, registerResponse)
    }
}

fun Route.registerProfile(authController: AuthController){
    authenticate {
        post("/registerProfile") {
            val username = call.principal<UserIdPrincipal>()!!.name
            val request = try {
                call.receive<ProfileRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
                return@post
            }

            val registerResponse = authController.tryRegisterProfile(
                username = username,
                profileName = request.profileName,
                email = request.email,
                phoneNumber = request.phoneNumber,
                newPassword = request.newPassword,
            );

            call.respond(HttpStatusCode.OK, registerResponse)
        }
    }
}

fun Route.login(authController: AuthController) {
    post("/login") {
        val request = try {
            call.receive<LoginRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val loginResponse = authController.tryLogin(request.username, request.password)

        call.respond(HttpStatusCode.OK, loginResponse)
    }
}

fun Route.sendEmailRequest(authController: AuthController) {
    post("/sendEmailRequest") {
        val request = try {
            call.receive<SendEmailRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val loginResponse = authController.tryRequestEmail(request.email)

        call.respond(HttpStatusCode.OK, loginResponse)
    }
}

fun Route.checkVerificationCode(authController: AuthController) {
    post("/checkVerificationCode") {
        val request = try {
            call.receive<VerifyCodeRequest>()
        }catch (e: ContentTransformationException){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val loginResponse = authController.tryVerifyCode(request.code)

        call.respond(HttpStatusCode.OK, loginResponse)
    }
}
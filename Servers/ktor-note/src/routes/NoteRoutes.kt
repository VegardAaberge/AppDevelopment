package com.androiddevs.routes

import com.androiddevs.data.*
import com.androiddevs.data.collections.Note
import com.androiddevs.data.requests.AddOwnerRequest
import com.androiddevs.data.requests.DeleteNoteRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.noteRoutes() {
    route("/getNotes") {
        authenticate {
            get {
                val email = call.principal<UserIdPrincipal>()!!.name

                val notes = getNotesForUser(email)
                call.respond(OK, notes)
            }
        }
    }
    route("/addOwnerToNote") {
        authenticate {
            post {
                val request = try {
                    call.receive<AddOwnerRequest>()
                }catch (e: ContentTransformationException){
                    call.respond(BadRequest)
                    return@post
                }

                if(!checkIfUserExists(request.owner)){
                    call.respond(OK, "No user with this email exist")
                    return@post
                }

                if(isOwnerOfNote(request.noteID, request.owner)){
                    call.respond(OK, "This user is already an owner of this note")
                    return@post
                }

                if(addOwnerToNote(request.noteID, request.owner)){
                    call.respond(OK, "${request.owner} can now see this note")
                }else{
                    call.respond(Conflict)
                }
            }
        }
    }
    route("/deleteNote") {
        authenticate {
            post {
                val email = call.principal<UserIdPrincipal>()!!.name
                val request = try {
                    call.receive<DeleteNoteRequest>()
                }catch (e: ContentTransformationException){
                    call.respond(BadRequest)
                    return@post
                }

                if(deleteNote(email, request.id)){
                    call.respond(OK)
                }else{
                    call.respond(Conflict)
                }
            }
        }
    }
    route("/addNote") {
        authenticate {
            post {
                val note = try {
                    call.receive<Note>()
                } catch (e: ContentTransformationException) {
                    call.respond(BadRequest)
                    return@post
                }
                if(saveNote(note)){
                    call.respond(OK)
                }else{
                    call.respond(Conflict)
                }
            }
        }
    }
}
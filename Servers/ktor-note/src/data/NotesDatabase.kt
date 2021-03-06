package com.androiddevs.data

import com.androiddevs.data.collections.Note
import com.androiddevs.data.collections.User
import com.androiddevs.security.checkHashForPassword
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.setValue

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("NotesDatabase")
private val users = database.getCollection<User>()
private val notes = database.getCollection<Note>()

suspend fun registerUser(user: User) : Boolean {
    return users.insertOne(user).wasAcknowledged()
}

suspend fun checkIfUserExists(email: String) : Boolean {
    return users.findOne(User::email eq email) != null
}

suspend fun checkPasswordForEnail(email: String, passwordToCheck: String) : Boolean {
    val passwordHashWithSalt = users.findOne(User::email eq email)?.password ?: return false
    return checkHashForPassword(passwordToCheck, passwordHashWithSalt)
}

suspend fun getNotesForUser(email: String): List<Note> {
    return notes.find(Note::owners contains email).toList()
}

suspend fun saveNote(note: Note): Boolean {
    val noteExist = notes.findOneById(note.id) != null
    return if(noteExist){
        notes.updateOneById(note.id, note).wasAcknowledged()
    }else{
        notes.insertOne(note).wasAcknowledged()
    }
}

suspend fun isOwnerOfNote(noteId: String, owner: String): Boolean{
    val note = notes.findOneById(noteId) ?: return false
    return owner in note.owners
}

suspend fun addOwnerToNote(noteId: String, owner: String) : Boolean {
    val owners = notes.findOneById(noteId)?.owners ?: return false
    return notes.updateOneById(noteId, setValue(Note::owners, owners + owner)).wasAcknowledged()
}

suspend fun deleteNote(email: String, noteId: String): Boolean {
    val note = notes.findOne(Note::id eq noteId, Note::owners contains email)
    note?.let {
        if(note.owners.size > 1){
            // the note has multiple owners, so just delete the email from owners
            val newOwners = note.owners - email
            val updateResult = notes.updateOne(Note::id eq note.id, setValue(Note::owners, newOwners))
            return updateResult.wasAcknowledged()
        }else{
            return notes.deleteOneById(note.id).wasAcknowledged()
        }
    } ?: return false
}
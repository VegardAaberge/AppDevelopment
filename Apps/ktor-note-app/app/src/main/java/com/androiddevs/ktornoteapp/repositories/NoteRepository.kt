package com.androiddevs.ktornoteapp.repositories

import android.app.Application
import android.util.Log
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androiddevs.ktornoteapp.data.local.NoteDao
import com.androiddevs.ktornoteapp.data.local.entities.LocallyDeletedNoteID
import com.androiddevs.ktornoteapp.data.local.entities.Note
import com.androiddevs.ktornoteapp.data.remote.NoteApi
import com.androiddevs.ktornoteapp.data.remote.requests.AccountRequest
import com.androiddevs.ktornoteapp.data.remote.requests.AddOwnerRequest
import com.androiddevs.ktornoteapp.data.remote.requests.DeleteNoteRequest
import com.androiddevs.ktornoteapp.other.Resource
import com.androiddevs.ktornoteapp.other.checkForInternetConnection
import com.androiddevs.ktornoteapp.other.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi,
    private val context: Application
) {
    suspend  fun insertNote(note: Note){
        val response = try {
            noteApi.addNote(note)
        }catch (e: Exception){
            null
        }
        if(response != null && response.isSuccessful){
            noteDao.insertNote(note.apply { isSynced = true })
        }else {
            noteDao.insertNote(note.apply { isSynced = false })
        }
    }

    suspend fun insertNotes(notes: List<Note>){
        notes.forEach { insertNote(it) }
    }

    suspend fun deleteNote(noteID: String){
        val response = try{
            noteApi.deleteNote(DeleteNoteRequest(noteID))
        }catch (e: Exception){
            null
        }

        noteDao.deleteNoteById(noteID)
        if(response == null || !response.isSuccessful){
            noteDao.insertLocallyDeleteNoteID(LocallyDeletedNoteID(noteID))
        }else{
            deleteLocallyDeletedNoteID(noteID)
        }
    }

    fun observeNoteById(noteID: String) = noteDao.observeNoteById(noteID)

    suspend fun deleteLocallyDeletedNoteID(deletedNoteID: String){
        noteDao.deleteLocallyDeletedNoteID(deletedNoteID)
    }

    private var curNotesResponse: Response<List<Note>>? = null

    suspend fun syncNotes() {
        val locallyDeletedNoteID = noteDao.getAllLocallyDeletedNoteID()
        locallyDeletedNoteID.forEach { id ->
            deleteNote(id.deletedNoteID)
        }

        val unsyncedNotes = noteDao.getAllUnsyncedNotes()
        unsyncedNotes.forEach { note ->
            insertNote(note)
        }

        curNotesResponse = noteApi.getNotes()
        curNotesResponse?.body()?.let { notes ->
            noteDao.deleteAllNotes()
            insertNotes(notes.onEach { note -> note.isSynced })
        }
    }

    suspend fun getNoteById(noteID: String) = noteDao.getNoteById(noteID)

    fun getAllNotes(): Flow<Resource<List<Note>>> {
        return networkBoundResource(
            query = {
                noteDao.getAllNotes()
            },
            fetch = {
                syncNotes()
                curNotesResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let{
                    insertNotes(it.onEach { note -> note.isSynced = true })
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

    suspend fun register(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            Authenticate(email, password)
        }catch (e: Exception) {
            Resource.error("Couldn't connect to the service, check your internet connection", null)
        }
    }

    suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            Authenticate(email, password)
        }catch (e: Exception) {
            Resource.error("Couldn't connect to the service, check your internet connection", null)
        }
    }

    private suspend fun Authenticate(email: String, password: String) : Resource<String> {
        val response = noteApi.login(AccountRequest(email, password))
        if(response.isSuccessful && response.body()!!.successful){
            return Resource.success(response.body()?.message)
        }else {
            return Resource.error(response.body()?.message ?: response.message(), null)
        }
    }

    suspend fun addOwnerToNote(owner: String, noteID: String) = withContext(Dispatchers.IO) {
        try {
            val response = noteApi.addOwnerToNote(AddOwnerRequest(owner, noteID))
            if(response.isSuccessful && response.body()!!.successful){
                Resource.success(response.body()?.message)
            }else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        }catch (e: Exception) {
            Log.e("KtorNoteApp", e.stackTraceToString())
            Resource.error("Couldn't connect to the service: ${e.message}", null)
        }
    }
}
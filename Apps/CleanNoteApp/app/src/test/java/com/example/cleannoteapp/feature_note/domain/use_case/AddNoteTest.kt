package com.example.cleannoteapp.feature_note.domain.use_case

import com.example.cleannoteapp.feature_note.data.repository.FakeNoteRepository
import com.example.cleannoteapp.feature_note.domain.model.InvalidNoteException
import com.example.cleannoteapp.feature_note.domain.model.Note
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


class AddNoteTest {

    lateinit var addNote: AddNote
    lateinit var getNotes: GetNotes
    lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNote(fakeNoteRepository)
    }


    @Test(expected = InvalidNoteException::class)
    fun `Insert note without title, return exception`() = runBlocking {
        val note = Note(
            title = "",
            content = "Content",
            timestamp = 1,
            color = 1
        )
        addNote(note)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note without content, return exception`() = runBlocking {
        val note = Note(
            title = "title",
            content = "",
            timestamp = 1,
            color = 1
        )
        addNote(note)
    }

    @Test
    fun `Insert note with valid content, note inserted`() = runBlocking {
        val note = Note(
            title = "title",
            content = "content",
            timestamp = 1,
            color = 1,
            id = 1
        )
        addNote(note)

        val result = fakeNoteRepository.getNoteById(1)

        Truth.assertThat(result).isNotNull()
    }
}
package com.example.noteappkmm.android.note_list

sealed class NoteListScreenEvent {
    data class Search(val text: String) : NoteListScreenEvent()
    data class DeleteNoteById(val noteId: Long) : NoteListScreenEvent()
    object ToggleSearch : NoteListScreenEvent()
    object LoadNotes : NoteListScreenEvent()

    data class EditNote(val id: Long): NoteListScreenEvent()
    object NewNote: NoteListScreenEvent()
}

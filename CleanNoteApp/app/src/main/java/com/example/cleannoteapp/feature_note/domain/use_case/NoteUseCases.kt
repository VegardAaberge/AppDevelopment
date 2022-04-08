package com.example.cleannoteapp.feature_note.domain.use_case

data class NoteUseCases (
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNode: AddNote,
    val getNote: GetNote,
)
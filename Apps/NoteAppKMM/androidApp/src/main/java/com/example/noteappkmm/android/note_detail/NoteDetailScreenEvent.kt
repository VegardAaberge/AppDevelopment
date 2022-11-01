package com.example.noteappkmm.android.note_detail

sealed class NoteDetailScreenEvent {
    data class TitleChanged(val title: String) : NoteDetailScreenEvent()
    data class TitleFocusedChanged(val focused: Boolean) : NoteDetailScreenEvent()
    data class ContentChanged(val content: String) : NoteDetailScreenEvent()
    data class ContentFocusedChanged(val focused: Boolean) : NoteDetailScreenEvent()
    object SaveNote : NoteDetailScreenEvent()
}

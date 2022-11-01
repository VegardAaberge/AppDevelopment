package com.example.noteappkmm.android.note_detail

import androidx.compose.ui.graphics.Color
import com.example.noteappkmm.domain.note.Note

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleTextFocused: Boolean = false,
    val noteContent: String = "",
    val isNoteContentFocused: Boolean = false,
    val noteColor: Long = 0xFFFFFFFF
)

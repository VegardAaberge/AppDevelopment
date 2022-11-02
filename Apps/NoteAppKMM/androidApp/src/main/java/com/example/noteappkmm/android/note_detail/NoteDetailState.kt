package com.example.noteappkmm.android.note_detail

import android.os.Parcelable
import com.example.noteappkmm.domain.note.Note
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleHintVisible: Boolean = false,
    val noteContent: String = "",
    val isNoteContentHintVisible: Boolean = false,
    val noteColor: Long = Note.generateRandomColor()
) : Parcelable {
    fun canSave() = noteTitle.isNotEmpty() && noteContent.isNotEmpty()
}

package com.example.cleannoteapp.feature_note.presentation.edit_note

import androidx.compose.ui.focus.FocusState

sealed class EditNoteEvent {
    data class SetNoteId(val id: Int): EditNoteEvent()
    data class EnteredTitle(val value: String): EditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): EditNoteEvent()
    data class EnteredContent(val value: String): EditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): EditNoteEvent()
    data class ChangedColor(val color: Int): EditNoteEvent()
    object SaveNote: EditNoteEvent()
}
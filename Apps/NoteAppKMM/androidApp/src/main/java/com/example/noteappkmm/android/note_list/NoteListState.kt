package com.example.noteappkmm.android.note_list

import android.os.Parcelable
import com.example.noteappkmm.domain.note.Note
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NoteListState(
    val notes: @RawValue List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
) : Parcelable
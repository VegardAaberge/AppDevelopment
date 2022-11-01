package com.example.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.android.note_list.NoteListState
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.note.SearchNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

const val NOTE_TITLE = "noteTitle"
const val NOTE_TITLE_FOCUSED = "noteTitleFocused"
const val NOTE_CONTENT = "noteContent"
const val NOTE_CONTENT_FOCUSED = "isNoteContentFocused"
const val NOTE_COLOR = "noteColor"

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()

    private val noteTitle = savedStateHandle.getStateFlow(NOTE_TITLE, "")
    private val isNoteTitleTextFocused = savedStateHandle.getStateFlow(NOTE_TITLE_FOCUSED, false)
    private val noteContent = savedStateHandle.getStateFlow(NOTE_CONTENT, "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow(NOTE_CONTENT_FOCUSED, false)
    private val noteColor = savedStateHandle.getStateFlow(NOTE_COLOR, 0xFFFFFFFF)

    val state = combine(
        noteTitle,
        isNoteTitleTextFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color -> {
        NoteDetailState(
             noteTitle= title,
             isNoteTitleTextFocused= isTitleFocused,
             noteContent= content,
             isNoteContentFocused= isContentFocused,
             noteColor= color
        )
    }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())
}
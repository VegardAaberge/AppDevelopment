package com.example.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val HANDLE = "noteDetail"
    val state = savedStateHandle.getStateFlow(HANDLE, NoteDetailSavedState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let {
            if(existingNoteId == -1L){
                return@let
            }
            existingNoteId = it
            viewModelScope.launch {
                noteDataSource.getNoteById(it)?.let { note ->
                    savedStateHandle[HANDLE] = state.value.copy(
                        noteTitle = note.title,
                        noteContent = note.content,
                        noteColor = note.colorHex,
                    )
                }
            }
        }
    }

    fun onEvent(event: NoteDetailScreenEvent){
        when(event){
            is NoteDetailScreenEvent.ContentChanged -> {
                savedStateHandle[HANDLE] = state.value.copy(
                    noteContent = event.content
                )
            }
            is NoteDetailScreenEvent.ContentFocusedChanged -> {
                savedStateHandle[HANDLE] = state.value.copy(
                    isNoteContentHintVisible = state.value.noteContent.isEmpty() && !event.focused
                )
            }
            is NoteDetailScreenEvent.TitleChanged -> {
                savedStateHandle[HANDLE] = state.value.copy(
                    noteTitle = event.title
                )
            }
            is NoteDetailScreenEvent.TitleFocusedChanged -> {
                savedStateHandle[HANDLE] = state.value.copy(
                    isNoteTitleHintVisible = state.value.noteTitle.isEmpty() && !event.focused
                )
            }
            NoteDetailScreenEvent.SaveNote -> {
                viewModelScope.launch {
                    noteDataSource.insertNote(
                        Note(
                            id = existingNoteId,
                            title = state.value.noteTitle,
                            content = state.value.noteContent,
                            colorHex = state.value.noteColor,
                            created = DateTimeUtil.now()
                        )
                    )
                    _hasNoteBeenSaved.value = true
                }
            }
        }
    }
}
package com.example.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.android.models.UIEvent
import com.example.noteappkmm.android.note_detail.NoteDetailState
import com.example.noteappkmm.android.note_detail.NoteDetailViewModel
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.note.SearchNotes
import com.example.noteappkmm.domain.time.DateTimeUtil
import com.example.noteappkmm.presentation.RedOrangeHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()
    private var notes = emptyList<Note>()

    val state = savedStateHandle.getStateFlow(HANDLE, NoteListState())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NoteListScreenEvent){
        when(event){
            is NoteListScreenEvent.LoadNotes -> {
                loadNotes()
            }
            is NoteListScreenEvent.DeleteNoteById -> {
                viewModelScope.launch {
                    noteDataSource.deleteNoteById(event.noteId)
                    loadNotes()
                }
            }
            is NoteListScreenEvent.Search -> {
                savedStateHandle[HANDLE] = state.value.copy(
                    searchText = event.text
                )
                getNotes()
            }
            is NoteListScreenEvent.ToggleSearch -> {
                val isSearchActive = state.value.isSearchActive
                savedStateHandle[HANDLE] = state.value.copy(
                    isSearchActive = !isSearchActive,
                    searchText = ""
                )
                if(isSearchActive){
                    getNotes()
                }
            }
            is NoteListScreenEvent.NewNote -> {
                navigateToNote("note_detail/-1L")
            }
            is NoteListScreenEvent.EditNote -> {
                navigateToNote("note_detail/${event.id}")
            }
        }
    }

    fun loadNotes() {
        viewModelScope.launch {
            notes = noteDataSource.getAllNotes()
            getNotes()
        }
    }

    fun getNotes(){
        savedStateHandle[HANDLE] = state.value.copy(
            notes = searchNotes.execute(notes, state.value.searchText)
        )
    }

    fun navigateToNote(path: String){
        viewModelScope.launch {
            _uiEvent.send(UIEvent.NavigateTo(path))
        }
    }

    companion object {
        const val HANDLE = "noteList"
    }
}
package com.example.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.note.NoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val NOTE_LIST = "noteList"
    val state = savedStateHandle.getStateFlow(NOTE_LIST, NoteListState())

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[NOTE_LIST] = state.value.copy(
                notes = noteDataSource.getAllNotes()
            )
        }
    }

    fun onSearchTextChange(text: String){
        savedStateHandle[NOTE_LIST] = state.value.copy(
            searchText = text
        )
    }

    fun onToggleSearch() {
        savedStateHandle[NOTE_LIST] = state.value.copy(
            isSearchActive = !state.value.isSearchActive,
            searchText = ""
        )
    }

    fun deleteNoteById(id: Long){
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }
}
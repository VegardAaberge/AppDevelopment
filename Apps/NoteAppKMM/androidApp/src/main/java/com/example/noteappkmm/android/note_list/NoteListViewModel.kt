package com.example.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.note.SearchNotes
import com.example.noteappkmm.domain.time.DateTimeUtil
import com.example.noteappkmm.presentation.RedOrangeHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NOTES = "notes"
private const val SEARCH_TEXT = "searchText"
private const val IS_SEARCH_ACTIVE = "isSearchActive"

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()

    private val notes = savedStateHandle.getStateFlow(NOTES, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SEARCH_TEXT, "")
    private val isSearchActive = savedStateHandle.getStateFlow(IS_SEARCH_ACTIVE, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())


    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[NOTES] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String){
        savedStateHandle[SEARCH_TEXT] = text
    }

    fun onToggleSearch() {
        savedStateHandle[IS_SEARCH_ACTIVE] = !isSearchActive.value
        if(!isSearchActive.value){
            savedStateHandle[SEARCH_TEXT] = ""
        }
    }

    fun deleteNoteById(id: Long){
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }
}
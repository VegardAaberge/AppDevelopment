package com.example.cleannoteapp.feature_note.presentation.edit_note

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleannoteapp.feature_note.domain.model.InvalidNoteException
import com.example.cleannoteapp.feature_note.domain.model.Note
import com.example.cleannoteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) :ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    fun onEvent(event: EditNoteEvent){
        when(event){
            is EditNoteEvent.SetNoteId -> {
                if(event.id != -1){
                    viewModelScope.launch {
                        noteUseCases.getNote(event.id)?.also { note ->
                            currentNoteId = note.id
                            _noteTitle.value = noteTitle.value.copy(
                                text = note.title,
                                isHintVisible = false
                            )
                            _noteContent.value = _noteContent.value.copy(
                                text = note.content,
                                isHintVisible = false
                            )
                            _noteColor.value = note.color
                        }
                    }
                }
            }
            is EditNoteEvent.EnteredTitle -> {
                ChangeTitle(_noteTitle, event.value)
            }
            is EditNoteEvent.ChangeTitleFocus -> {
                ChangeFocus(_noteTitle, event.focusState.isFocused)
            }
            is EditNoteEvent.EnteredContent -> {
                ChangeTitle(_noteContent, event.value)
            }
            is EditNoteEvent.ChangeContentFocus -> {
                ChangeFocus(_noteContent, event.focusState.isFocused)
            }
            is EditNoteEvent.ChangedColor -> {
                _noteColor.value = event.color
            }
            is EditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try{
                        noteUseCases.addNode(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    fun ChangeTitle(textField: MutableState<NoteTextFieldState>, newValue: String){
        textField.value = textField.value.copy(
            text = newValue
        )
    }

    fun ChangeFocus(textField: MutableState<NoteTextFieldState>, isFocused: Boolean){
        textField.value = textField.value.copy(
            isHintVisible = !isFocused && textField.value.text.isBlank()
        )
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}

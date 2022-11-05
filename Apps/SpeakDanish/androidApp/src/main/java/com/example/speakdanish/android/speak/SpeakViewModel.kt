package com.example.speakdanish.android.speak

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.speakdanish.android.models.UIEvent
import com.example.speakdanish.domain.Recording
import com.example.speakdanish.domain.RecordingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class SpeakViewModel @Inject constructor(
    private val recordingDataSource: RecordingDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = savedStateHandle.getStateFlow(HANDLE, SpeakState())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        savedStateHandle[HANDLE] = state.value.copy(
            sentence = "Jeg hedder Emma",
            recordings = listOf(
                Recording(
                    content = "",
                    created = LocalDateTime.now().minusMinutes(15).toKotlinLocalDateTime()
                ),
                Recording(
                    content = "",
                    created = LocalDateTime.now().toKotlinLocalDateTime()
                ),
            )
        )
    }

    fun onEvent(event: SpeakScreenEvent){
        when(event) {
            is SpeakScreenEvent.ListenAgain -> {
                TODO()
            }
            is SpeakScreenEvent.ListenToRecording -> {
                TODO()
            }
            is SpeakScreenEvent.RecordTapped -> {
                TODO()
            }
            is SpeakScreenEvent.SettingsTapped -> {
                TODO()
            }
            is SpeakScreenEvent.SubmitTapped -> {
                TODO()
            }
        }
    }

    companion object {
        const val HANDLE = "speak"
    }
}
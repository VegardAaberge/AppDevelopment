package com.example.speakdanish.android.recordings

import android.content.Intent
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakdanish.android.models.UIEvent
import com.example.speakdanish.domain.RecordingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class RecordingsViewModel @Inject constructor(
    private val recordingDataSource: RecordingDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = savedStateHandle.getStateFlow(HANDLE, RecordingsState())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    lateinit var textToSpeech: TextToSpeech
    private var selectedVoice: Voice? = null
    lateinit private var voices: List<Voice>

    init {
        loadRecordings()
    }

    fun onEvent(event: RecordingsScreenEvent) {
        when (event) {
            is RecordingsScreenEvent.DeleteRecording -> {
                viewModelScope.launch {
                    recordingDataSource.deleteRecordingById(event.recordId)
                    loadRecordings()
                }
            }
            is RecordingsScreenEvent.ListenToRecording -> {
                state.value.recordings.firstOrNull { it.id == event.recordId }?.let {
                    playRecording(it.path)
                }
            }
            is RecordingsScreenEvent.PlayRecording -> {
                state.value.recordings.firstOrNull { it.id == event.recordId }?.let {
                    playNativeSpeech(it.id, it.text, event.fast)
                }
            }
            is RecordingsScreenEvent.NewRecording -> {
                viewModelScope.launch {
                    state.value.recordings.firstOrNull { it.id == event.recordId }?.let {
                        _uiEvent.send(UIEvent.PopPageWithResult(it.text))
                    }
                }
            }
        }
    }

    fun textToSpeechInitalised(status: Int) = viewModelScope.launch {
        if (status != TextToSpeech.ERROR) {
            textToSpeech.setLanguage(Locale("da_DK"));
            voices = textToSpeech.voices?.filter { it.locale.displayLanguage == "Danish" && !it.isNetworkConnectionRequired } ?: emptyList()
        } else {
            Log.i("XXX", "Internal Google engine init error.")
        }
    }

    private fun loadRecordings(){
        viewModelScope.launch {
            val recordings = recordingDataSource.getAllRecordings()
            savedStateHandle[HANDLE] = state.value.copy(
                recordings = recordings
            )
        }
    }

    private fun playNativeSpeech(id: String, sentence: String, fast: Boolean){
        if(selectedVoice == null){
            selectedVoice = if(voices.isNotEmpty()) voices.random() else null
        }
        if(selectedVoice != null){
            textToSpeech.setVoice(selectedVoice)
            textToSpeech.setSpeechRate(if(fast) 0.9f else 0.6f)
            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) = setPlayRecordId(id)
                override fun onDone(utteranceId: String?) = setPlayRecordId(null)
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) = setPlayRecordId(null)
            })
            textToSpeech.speak(sentence.subSequence(0, sentence.length), TextToSpeech.QUEUE_ADD, null, "1")
        }else{
            InstallVoiceData()
        }
    }

    private fun setPlayRecordId(id: String?){
        savedStateHandle[HANDLE]= state.value.copy(
            playRecordId = id
        )
    }

    private fun playRecording(path: String) {
        val mp = MediaPlayer()
        try {
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun InstallVoiceData() = viewModelScope.launch {
        val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.tts")
        _uiEvent.send(UIEvent.StartActivity(intent))
    }

    companion object {
        const val HANDLE = "recordings"
    }
}

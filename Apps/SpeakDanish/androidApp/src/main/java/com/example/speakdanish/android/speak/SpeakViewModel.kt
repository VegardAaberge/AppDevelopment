package com.example.speakdanish.android.speak

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakdanish.android.models.UIEvent
import com.example.speakdanish.domain.Recording
import com.example.speakdanish.domain.RecordingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject


@HiltViewModel
class SpeakViewModel @Inject constructor(
    private val recordingDataSource: RecordingDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = savedStateHandle.getStateFlow(HANDLE, SpeakState())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    lateinit var googleTTS: TextToSpeech
    lateinit var recorder: MediaRecorder
    private var selectedVoice: Voice? = null
    lateinit private var voices: List<Voice>

    init {
        savedStateHandle[HANDLE] = state.value.copy(
            sentence = "Gå ikke glip af denne fantastiske forestilling, en enkel, men kunstnerisk måde at vise livet på.",
        )
    }

    fun onEvent(event: SpeakScreenEvent){
        when(event) {
            is SpeakScreenEvent.ListenAgain -> {
                val sentence = state.value.sentence
                if(selectedVoice == null){
                    selectedVoice = if(voices.isNotEmpty()) voices.random() else null
                }
                if(selectedVoice != null){
                    googleTTS.setVoice(selectedVoice)
                    googleTTS.speak(sentence.subSequence(0, sentence.length), TextToSpeech.QUEUE_ADD, null, "1")
                }else{
                    InstallVoiceData()
                }
            }
            is SpeakScreenEvent.ListenToRecording -> {
                state.value.recording?.let {
                    playRecording(it)
                }
            }
            is SpeakScreenEvent.RecordTapped -> {
                when(event.motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        startRecording(event.filePath)
                    }
                    MotionEvent.ACTION_UP -> {
                        stopRecording(event.filePath)
                    }
                }
            }
            is SpeakScreenEvent.SettingsTapped -> {
                TODO()
            }
            is SpeakScreenEvent.SubmitTapped -> {
                TODO()
            }
        }
    }

    fun startRecording(filePath: String){
        savedStateHandle[HANDLE] = state.value.copy(
            isRecording = true
        )
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(filePath);
        recorder.prepare();
        recorder.start()
    }

    fun stopRecording(filePath: String){
        recorder.stop()
        val recording = Recording(
            content = filePath,
            created = LocalDateTime.now().toKotlinLocalDateTime()
        )
        savedStateHandle[HANDLE] = state.value.copy(
            isRecording = false,
            recording = recording
        )
    }

    fun GoogleTTSInitalised(status: Int) = viewModelScope.launch {
        if (status != TextToSpeech.ERROR) {
            Log.i("XXX", "Google tts initialized")
            googleTTS.setLanguage(Locale("da_DK"));
            voices = googleTTS.voices?.filter { it.locale.displayLanguage == "Danish" && !it.isNetworkConnectionRequired } ?: emptyList()
        } else {
            Log.i("XXX", "Internal Google engine init error.")
        }
    }

    fun playRecording(recording: Recording) {
        //set up MediaPlayer
        val mp = MediaPlayer()
        try {
            mp.setDataSource(recording.content)
            mp.prepare()
            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun InstallVoiceData() = viewModelScope.launch {
        val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.tts")
        _uiEvent.send(UIEvent.StartActivity(intent))
    }

    companion object {
        const val HANDLE = "speak"
    }
}
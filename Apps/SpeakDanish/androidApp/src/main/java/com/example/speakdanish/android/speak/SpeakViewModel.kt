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
import com.example.speakdanish.android.destinations.RecordingsScreenDestination
import com.example.speakdanish.android.models.UIEvent
import com.example.speakdanish.domain.GetRandomSentence
import com.example.speakdanish.domain.Recording
import com.example.speakdanish.domain.RecordingDataSource
import com.ramcosta.composedestinations.result.NavResult
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

    private val getRandomSentence = GetRandomSentence()
    val state = savedStateHandle.getStateFlow(HANDLE, SpeakState())

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    lateinit var googleTTS: TextToSpeech
    lateinit var recorder: MediaRecorder
    private var selectedVoice: Voice? = null
    lateinit private var voices: List<Voice>

    init {
        if(state.value.sentence.isEmpty()){
            savedStateHandle[HANDLE] = state.value.copy(
                sentence = getRandomSentence.execute(null),
            )
        }
    }

    fun init(result: String) {
        savedStateHandle[HANDLE] = state.value.copy(
            sentence = result,
        )
    }

    fun onEvent(event: SpeakScreenEvent){
        when(event) {
            is SpeakScreenEvent.ListenAgain -> {
                playNativeSpeech(state.value.sentence, event.fast)
            }
            is SpeakScreenEvent.ListenToRecording -> {
                state.value.recording?.let {
                    playRecording(it.path)
                }
            }
            is SpeakScreenEvent.RecordTapped -> {

                var recording = if(state.value.recording == null){
                    Recording(
                        text = state.value.sentence,
                        created = LocalDateTime.now().toKotlinLocalDateTime()
                    )
                } else state.value.recording!!

                val newRecording = recording.copy(
                    path = event.saveDirectory + recording.id
                )

                when(event.motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        savedStateHandle[HANDLE] = state.value.copy(
                            isRecording = true,
                            recording = newRecording
                        )
                        startRecording(newRecording.path)
                    }
                    MotionEvent.ACTION_UP -> {
                        savedStateHandle[HANDLE] = state.value.copy(
                            isRecording = false,
                        )
                        recorder.stop()
                    }
                }
            }
            is SpeakScreenEvent.HistoryTapped -> {
                viewModelScope.launch {
                    _uiEvent.send(UIEvent.NavigateTo(RecordingsScreenDestination))
                }
            }
            is SpeakScreenEvent.SubmitTapped -> {
                viewModelScope.launch {
                    recordingDataSource.insertRecording(state.value.recording!!)
                }
                savedStateHandle[HANDLE] = SpeakState(
                    sentence = getRandomSentence.execute(state.value.sentence)
                )
            }
            SpeakScreenEvent.SkipTapped -> {
                savedStateHandle[HANDLE] = SpeakState(
                    sentence = getRandomSentence.execute(state.value.sentence)
                )
            }
        }
    }

    fun playNativeSpeech(sentence: String, fast: Boolean){
        if(selectedVoice == null){
            selectedVoice = if(voices.isNotEmpty()) voices.random() else null
        }
        if(selectedVoice != null){
            googleTTS.setVoice(selectedVoice)
            googleTTS.setSpeechRate(if(fast) 0.9f else 0.6f)
            googleTTS.speak(sentence.subSequence(0, sentence.length), TextToSpeech.QUEUE_ADD, null, "1")
        }else{
            InstallVoiceData()
        }
    }

    fun startRecording(filePath: String){
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(filePath);
        recorder.prepare();
        recorder.start()
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

    fun playRecording(path: String) {
        //set up MediaPlayer
        val mp = MediaPlayer()
        try {
            mp.setDataSource(path)
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
package com.example.speakdanish.android.speak

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.speakdanish.android.speak.components.SpeakTextItem
import com.example.speakdanish.android.MyApplicationTheme
import com.example.speakdanish.android.components.CollectEventFlow
import com.example.speakdanish.android.speak.components.RecordItem
import com.example.speakdanish.domain.Recording
import com.example.speakdanish.utils.Constants
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpeakScreen(
    navController: NavController,
    viewModel: SpeakViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    CollectEventFlow(viewModel, navController, viewModel.uiEvent)

    SpeakBody(
        sentence = state.sentence,
        recordings = state.recordings,
        listenAgain = { viewModel.onEvent(SpeakScreenEvent.ListenAgain) },
        recordTapped = { viewModel.onEvent(SpeakScreenEvent.RecordTapped) },
        settingsTapped = { viewModel.onEvent(SpeakScreenEvent.SettingsTapped) },
        submitTapped = { viewModel.onEvent(SpeakScreenEvent.SubmitTapped)},
        listenToRecording = { viewModel.onEvent(SpeakScreenEvent.ListenToRecording(it)) }
    )
}

@Composable
fun SpeakBody(
    sentence: String,
    recordings: List<Recording>,
    listenAgain: () -> Unit,
    recordTapped: () -> Unit,
    settingsTapped: () -> Unit,
    submitTapped: () -> Unit,
    listenToRecording: (String) -> Unit,
) {
    val context = LocalContext.current

    TextToSpeech()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Speak Danish")
                },
                actions = {
                    IconButton(
                        onClick = {
                            settingsTapped()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                        )
                    }

                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(
                text = "Speak this sentence",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            SpeakTextItem(
                text = sentence,
                listenAction = {
                    googleTTS.setVoice(voices.random())
                    googleTTS.speak(sentence.subSequence(0, sentence.length), TextToSpeech.QUEUE_ADD, null, "1")
                }
            )
            RecordItem(
                recordAction = recordTapped
            )
            Text(
                text = "Your recordings",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
            recordings.forEach { recording ->
                val text = remember(recording.created){
                    val date = recording.created.toJavaLocalDateTime()
                    val dateTimeFormat = DateTimeFormatter.ofPattern(
                        if(date.toLocalDate() == LocalDate.now()) Constants.TIME_FORMAT else Constants.DATE_TIME_FORMAT,
                        Locale("en")
                    )
                    "Recording " + date.format(dateTimeFormat)
                }
                SpeakTextItem(
                    text = text,
                    listenAction = {
                        listenToRecording(recording.id)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    submitTapped()
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SpeakBodyPreview() {
    MyApplicationTheme {
        SpeakBody(
            sentence = "Gå ikke glip af denne fantastiske forestilling, en enkel, men kunstnerisk måde at vise livet på.",
            recordings = listOf(
                Recording(
                    content = "",
                    created = LocalDateTime.now().minusMinutes(15).toKotlinLocalDateTime()
                ),
                Recording(
                    content = "",
                    created = LocalDateTime.now().toKotlinLocalDateTime()
                ),
            ),
            listenAgain = { },
            recordTapped = { },
            settingsTapped = { },
            submitTapped = { },
            listenToRecording = { }
        )
    }
}

lateinit var googleTTS: TextToSpeech
lateinit var voices: List<Voice>

@Composable
fun TextToSpeech() {

    googleTTS = TextToSpeech(LocalContext.current.applicationContext) { status ->
        if (status != TextToSpeech.ERROR) {
            Log.i("XXX", "Google tts initialized")
            googleTTS.setLanguage(Locale("da_DK"));
            voices = googleTTS.voices.filter { it.locale.displayLanguage == "Danish" && !it.isNetworkConnectionRequired }
        } else {
            Log.i("XXX", "Internal Google engine init error.")
        }
    }

    //InstallVoiceData()
}

@Composable
private fun InstallVoiceData() {
    val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.setPackage("com.google.android.tts" /*replace with the package name of the target TTS engine*/)

    Log.v("TAG", "Installing voice data: " + intent.toUri(0))
    ContextCompat.startActivity(LocalContext.current.applicationContext, intent, null)
}

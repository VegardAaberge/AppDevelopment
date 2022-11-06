package com.example.speakdanish.android.speak

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.speakdanish.android.AppTheme
import com.example.speakdanish.android.components.CollectEventFlow
import com.example.speakdanish.android.speak.components.RecordItem
import com.example.speakdanish.android.speak.components.SpeakTextItem
import com.example.speakdanish.domain.Recording
import com.example.speakdanish.utils.Constants
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

lateinit private var launcher: ManagedActivityResultLauncher<String, Boolean>

@Composable
fun SpeakScreen(
    navController: NavController,
    viewModel: SpeakViewModel = hiltViewModel()
) {
    val context = LocalContext.current.applicationContext
    val state by viewModel.state.collectAsState()
    CollectEventFlow(viewModel, navController, viewModel.uiEvent)

    TextToSpeech(
        viewModel = viewModel,
        state = state,
        context = context
    )

    SpeakBody(
        state = state,
        listenAgain = { viewModel.onEvent(SpeakScreenEvent.ListenAgain) },
        settingsTapped = { viewModel.onEvent(SpeakScreenEvent.SettingsTapped) },
        submitTapped = { viewModel.onEvent(SpeakScreenEvent.SubmitTapped)},
        listenToRecording = { viewModel.onEvent(SpeakScreenEvent.ListenToRecording(it)) },
        recordTapped = { motionEvent ->
            val absolutePath = context.getFilesDir().absolutePath + File.separator + "test"

            // Check permission
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) -> {
                    viewModel.onEvent(SpeakScreenEvent.RecordTapped(absolutePath, motionEvent))
                }
                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        }
    )
}

@Composable
fun SpeakBody(
    state: SpeakState,
    listenAgain: () -> Unit,
    recordTapped: (MotionEvent) -> Unit,
    settingsTapped: () -> Unit,
    submitTapped: () -> Unit,
    listenToRecording: (String) -> Unit,
) {
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
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(state.isRecording){
                    Box(modifier = Modifier
                        .padding(start = 4.dp, end = 8.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                    )
                }
                Text(
                    text = if(state.isRecording) "Recording" else "Speak this sentence",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            SpeakTextItem(
                text = state.sentence,
                listenAction = listenAgain
            )
            RecordItem(
                isRecording = state.isRecording,
                recordTapped = recordTapped,
            )
            Text(
                text = "Your recording",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
            state.recording?.let { recording ->
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
                    .padding(vertical = 16.dp)
                    .height(40.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
fun TextToSpeech(
    viewModel: SpeakViewModel,
    state: SpeakState,
    context: Context
) {
    viewModel.googleTTS = remember {
        TextToSpeech(context) { status ->
            viewModel.GoogleTTSInitalised(status)
        }
    }
    viewModel.recorder = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        }else{
            MediaRecorder()
        }
    }

    launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SpeakBodyPreview() {
    AppTheme {
        SpeakBody(
            state = SpeakState(
                sentence = "Gå ikke glip af denne fantastiske forestilling, en enkel, men kunstnerisk måde at vise livet på.",
                recording = Recording(
                    content = "",
                    created = LocalDateTime.now().minusMinutes(15).toKotlinLocalDateTime()
                ),
                isRecording = true
            ),
            listenAgain = { },
            recordTapped = { },
            settingsTapped = { },
            submitTapped = { },
            listenToRecording = { }
        )
    }
}

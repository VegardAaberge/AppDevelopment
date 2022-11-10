package com.example.speakdanish.android.recordings

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.speakdanish.android.AppTheme
import com.example.speakdanish.android.R
import com.example.speakdanish.android.components.CollectEventFlow
import com.example.speakdanish.android.speak.components.SpeakTextItem
import com.example.speakdanish.domain.Recording
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@Destination
@Composable
fun RecordingsScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>,
    viewModel: RecordingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current.applicationContext
    val state by viewModel.state.collectAsState()
    CollectEventFlow(viewModel, navigator, viewModel.uiEvent, resultNavigator)

    TextToSpeech(
        viewModel = viewModel,
        context = context
    )

    RecordingsBody(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun RecordingsBody(
    state: RecordingsState,
    onEvent: (RecordingsScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(RecordingsScreenEvent.GoBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button",
                        )
                    }
                },
                title = {
                    Text(text = "Recordings")
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(top = 8.dp, bottom = 32.dp)
        ){
            items(state.recordings){recording ->
                RecordingItem(
                    recording = recording,
                    playRecordId = state.playRecordId,
                    onEvent = onEvent
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecordingItem(
    recording: Recording,
    playRecordId: String?,
    onEvent: (RecordingsScreenEvent) -> Unit
) {
    val dismissState = rememberDismissState()
    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onEvent(RecordingsScreenEvent.DeleteRecording(recording.id))
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(
            DismissDirection.EndToStart
        ),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
        },
        background = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.White
                    else -> Color(0xFFFF3333)
                }
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = Dp(20f)),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Delete",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        },
        dismissContent = {
            Card(
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 4.dp)
                ) {
                    SpeakTextItem(
                        text = recording.text,
                        isListening = recording.id == playRecordId,
                        listenAction = { fast ->
                            onEvent(RecordingsScreenEvent.PlayRecording(recording.id, fast))
                        },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        onEvent(RecordingsScreenEvent.ListenToRecording(recording.id))
                    }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_audio_file_24
                            ),
                            tint = Color(0xFFC41E3A),
                            contentDescription = "Recording"
                        )
                    }
                    IconButton(onClick = {
                        onEvent(RecordingsScreenEvent.NewRecording(recording.id))
                    }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_play_circle_outline_24
                            ),
                            tint = Color(0xFF228B22),
                            contentDescription = "Recording"
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TextToSpeech(
    viewModel: RecordingsViewModel,
    context: Context
) {
    viewModel.textToSpeech = remember {
        TextToSpeech(context) { status ->
            viewModel.textToSpeechInitalised(status)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SpeakBodyPreview() {
    val sentence = "Gå ikke glip af denne fantastiske forestilling, en enkel, men kunstnerisk måde at vise livet på."
    AppTheme {
        RecordingsBody(
            state = RecordingsState(
                recordings = listOf(
                    Recording(
                        path = "",
                        text = sentence,
                        created = LocalDateTime.now().toKotlinLocalDateTime()
                    ),
                    Recording(
                        path = "",
                        text = sentence,
                        created = LocalDateTime.now().toKotlinLocalDateTime()
                    ),
                )
            ),
            onEvent = { },
        )
    }
}

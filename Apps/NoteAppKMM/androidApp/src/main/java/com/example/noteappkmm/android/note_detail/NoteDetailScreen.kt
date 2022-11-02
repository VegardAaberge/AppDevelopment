package com.example.noteappkmm.android.note_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteappkmm.android.components.CollectEventFlow
import com.example.noteappkmm.android.note_detail.components.TransparentHintTextField

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteDetailScreen(
    noteId: Long,
    navController: NavController,
    viewModel: NoteDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    CollectEventFlow(viewModel, navController, viewModel.uiEvent)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(state.canSave()){
                        viewModel.onEvent(NoteDetailScreenEvent.SaveNote)
                    }
                },
                backgroundColor = if(state.canSave()){
                    Color.Black
                } else Color.LightGray
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Add note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleHintVisible,
                onValueChange = {
                    viewModel.onEvent(NoteDetailScreenEvent.TitleChanged(it))
                },
                onFocusedChanged = {
                    viewModel.onEvent(NoteDetailScreenEvent.TitleFocusedChanged(it.isFocused))
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter some content...",
                isHintVisible = state.isNoteContentHintVisible,
                onValueChange = {
                    viewModel.onEvent(NoteDetailScreenEvent.ContentChanged(it))
                },
                onFocusedChanged = {
                    viewModel.onEvent(NoteDetailScreenEvent.ContentFocusedChanged(it.isFocused))
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
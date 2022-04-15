package com.example.cleannoteapp.feature_note.presentation.edit_note.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleannoteapp.feature_note.domain.model.Note
import com.example.cleannoteapp.feature_note.presentation.edit_note.EditNoteEvent
import com.example.cleannoteapp.feature_note.presentation.edit_note.EditNoteViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteColorsRow(
    noteBackgroundAnimatable: Animatable<Color, AnimationVector4D>,
    viewModel: EditNoteViewModel = hiltViewModel()
) {val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Note.noteColors.forEach { color ->
            val colorInt = color.toArgb()
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = 3.dp,
                        color = if (viewModel.noteColor.value == colorInt) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        scope.launch {
                            noteBackgroundAnimatable.animateTo(
                                targetValue = Color(colorInt),
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        }
                        viewModel.onEvent(EditNoteEvent.ChangedColor(colorInt))
                    }
            )
        }
    }
}
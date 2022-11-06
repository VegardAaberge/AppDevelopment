package com.example.speakdanish.android.speak.components

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.speakdanish.android.AppTheme
import com.example.speakdanish.android.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnScope.RecordItem(
    isRecording: Boolean,
    recordTapped: (MotionEvent) -> Unit,
) {
    val color = if(isRecording) {
        Color(ColorUtils.blendARGB(MaterialTheme.colors.secondaryVariant.toArgb(), Color.Black.toArgb(), 0.2f))
    }else MaterialTheme.colors.secondaryVariant

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 50.dp)
            .clip(CircleShape)
            .background(color)
            .pointerInteropFilter {
                recordTapped(it)
                true
            }
            .align(Alignment.CenterHorizontally),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_mic_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(180.dp)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RecordItemPreview() {
    AppTheme {
        Column {
            RecordItem(
                recordTapped = {},
                isRecording = true
            )
        }
    }
}
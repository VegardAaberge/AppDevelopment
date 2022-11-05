package com.example.speakdanish.android.speak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speakdanish.android.R

@Composable
fun ColumnScope.RecordItem(
    recordAction: () -> Unit
) {
    val blueColor = Color(0xFF0099FF)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 50.dp)
            .clip(CircleShape)
            .background(blueColor)
            .clickable {
                recordAction()
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
    Column {
        RecordItem(
            recordAction = {}
        )
    }
}
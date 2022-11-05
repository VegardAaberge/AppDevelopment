package com.example.speakdanish.android.speak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speakdanish.android.MyApplicationTheme
import com.example.speakdanish.android.R

@Composable
fun SpeakTextItem(
    text: String,
    listenAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val blueColor = Color(0xFF0099FF)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(MutableInteractionSource(), null) {
                listenAction()
            }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(blueColor)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_volume_up_24),
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SpeakTextItemPreview() {
    MyApplicationTheme {
        SpeakTextItem(
            "Jeg hedder",
            listenAction = {}
        )
    }
}
package com.example.speakdanish.android.speak.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speakdanish.android.AppTheme
import com.example.speakdanish.android.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpeakTextItem(
    isListening: Boolean,
    text: String,
    listenAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    var highVolume by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = isListening){
        while(isListening){
            highVolume = !highVolume
            delay(500)
        }
        highVolume = true
    }

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
                .background(MaterialTheme.colors.secondaryVariant)
        ) {
            AnimatedContent(
                targetState = highVolume,
                transitionSpec =  {
                    fadeIn(
                        animationSpec = tween(250)
                    ) with fadeOut(
                        animationSpec = tween(250, delayMillis = 125)
                    )
        },
            ) { targetState ->
                Icon(
                    painter = if(targetState) {
                        painterResource(id = R.drawable.baseline_volume_up_24)
                    } else painterResource(id = R.drawable.baseline_volume_down_24),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(4.dp)
                        .offset(x = if (targetState) 0.dp else -2.dp)
                )
            }


        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SpeakTextItemPreview() {
    AppTheme {
        SpeakTextItem(
            isListening = true,
            "Jeg hedder",
            listenAction = {}
        )
    }
}
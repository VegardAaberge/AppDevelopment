package com.example.composecourse

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

var mediaPlayer : MediaPlayer? = null

@Destination
@Composable
fun MusicKnobScreen(){
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                .padding(vertical = 30.dp, horizontal = 20.dp)
        ){
            var percent by remember {
                mutableStateOf(0f)
            }
            var barCount = 10
            MusicKnob(
                modifier = Modifier.size(150.dp)
            ){
                percent = it

                if(it >= 0.05){
                    if(mediaPlayer == null){
                        mediaPlayer = MediaPlayer.create(context, R.raw.snowman)
                        mediaPlayer!!.start()
                    }

                    mediaPlayer!!.setVolume(it, it)

                }else if(mediaPlayer != null && it < 0.05){
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            VolumeBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                activeBars = (barCount * percent).roundToInt(),
                barCount = barCount)
        }
    }
}

@Composable
fun VolumeBar(
    modifier: Modifier = Modifier,
    activeBars : Int = 0,
    barCount: Int = 10
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }
        Canvas(modifier = modifier){
            for (i in 0 until barCount){
                drawRoundRect(
                    color = if(i in 0..activeBars) Color.Green else Color.DarkGray,
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f,0f),
                    size = Size(barWidth, constraints.maxHeight.toFloat()),
                    cornerRadius = CornerRadius(0f)
                )
            }
        }
    }
}

@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember{
        mutableStateOf(limitingAngle)
    }
    var touchx by remember {
        mutableStateOf(0f)
    }
    var touchy by remember {
        mutableStateOf(0f)
    }
    var centerX by remember {
        mutableStateOf(0f)
    }
    var centerY by remember {
        mutableStateOf(0f)
    }

    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "Music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f
            }
            .pointerInput(Unit) {
                detectDragGestures {
                        change,
                        dragAmount
                    ->

                    touchx = change.position.x
                    touchy = change.position.y

                    val radiants = -atan2(centerX - touchx, centerY - touchy)
                    val angle = (radiants * 180 / PI).toFloat()

                    if (angle !in -limitingAngle..limitingAngle) {
                        val fixedAngle = if (angle in -180f..limitingAngle) {
                            360f + angle
                        } else {
                            angle
                        }

                        if((fixedAngle - rotation) in -60f..60f){
                            rotation = fixedAngle
                            val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                            onValueChange(percent)
                        }
                    }
                }
            }
            .rotate(rotation)
    )
}

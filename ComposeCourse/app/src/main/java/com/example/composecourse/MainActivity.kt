package com.example.composecourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            
        }
    }
}

@@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember{
        mutableStateOf(limitingAngle)
    }
    var touchx by remember {
        mutableStateOf(0)
    }
    var touchy by remember {
        mutableStateOf(0)
    }
    var centerX by remember {
        mutableStateOf(0)
    }

}





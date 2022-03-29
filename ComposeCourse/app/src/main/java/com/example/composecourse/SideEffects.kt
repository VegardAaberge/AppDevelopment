package com.example.composecourse

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

class SideEffects {

    @Composable
    fun Setup(){
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(scaffoldState = scaffoldState) {
            // Useful for network calls
            var counter = produceState(initialValue = 0){
                delay(3000L)
                value = 4
            }
            if(counter.value % 5 == 0 && counter.value > 0){
                LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar("Hello")
                }
            }
            Button(onClick = {  }) {
                Text(text = "Click me: ${counter.value}")
            }
        }
    }

    @Composable
    private fun CreateClickMe(backPressedDispatcher : OnBackPressedDispatcher) {
        var callback = remember {
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    // Do something
                }
            }
        }

        DisposableEffect(key1 = backPressedDispatcher) {
            backPressedDispatcher.addCallback(callback)
            onDispose {
                callback.remove()
            }
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Click me")
        }
    }
}
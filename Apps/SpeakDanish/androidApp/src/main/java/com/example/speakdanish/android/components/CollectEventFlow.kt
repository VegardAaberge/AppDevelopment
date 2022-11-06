package com.example.speakdanish.android.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.speakdanish.android.models.UIEvent
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.flow.Flow

@Composable
fun CollectEventFlow(
    viewModel: ViewModel,
    navigator: DestinationsNavigator,
    eventChannelFlow: Flow<UIEvent>,
    resultNavigator: ResultBackNavigator<String>? = null,
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context){
        eventChannelFlow.collect { event ->
            when(event){
                is UIEvent.PopPage -> {
                    navigator.popBackStack()
                }
                is UIEvent.PopPageWithResult -> {
                    resultNavigator!!.navigateBack(event.content)
                }
                is UIEvent.NavigateTo -> {
                    navigator.navigate(event.direction)
                }
                is UIEvent.StartActivity -> {
                    ContextCompat.startActivity(context, event.intent, null)
                }
            }
        }
    }
}
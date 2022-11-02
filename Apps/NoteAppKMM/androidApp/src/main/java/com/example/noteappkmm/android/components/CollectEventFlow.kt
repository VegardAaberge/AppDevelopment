package com.example.noteappkmm.android.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.noteappkmm.android.models.UIEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun CollectEventFlow(
    viewModel: ViewModel,
    navController: NavController,
    eventChannelFlow: Flow<UIEvent>,
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context){
        eventChannelFlow.collect { event ->
            when(event){
                UIEvent.PopPage -> {
                    navController.popBackStack()
                }
                is UIEvent.NavigateTo -> {
                    navController.navigate(event.path)
                }
            }
        }
    }
}
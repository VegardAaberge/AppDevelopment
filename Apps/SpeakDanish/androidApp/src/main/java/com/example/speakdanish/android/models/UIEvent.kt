package com.example.speakdanish.android.models

import android.content.Intent
import com.example.speakdanish.android.destinations.DirectionDestination

sealed class UIEvent {
    object PopPage : UIEvent()
    data class NavigateTo(val direction: DirectionDestination) : UIEvent()
    data class StartActivity(val intent: Intent) : UIEvent()
    data class PopPageWithResult(val content: String) : UIEvent()
}
package com.example.speakdanish.android.models

import android.content.Intent

sealed class UIEvent {
    object PopPage : UIEvent()
    data class NavigateTo(val path: String) : UIEvent()
    data class StartActivity(val intent: Intent) : UIEvent()
}
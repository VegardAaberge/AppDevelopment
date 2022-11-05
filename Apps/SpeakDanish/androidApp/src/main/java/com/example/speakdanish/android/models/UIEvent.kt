package com.example.speakdanish.android.models

sealed class UIEvent {
    object PopPage : UIEvent()
    data class NavigateTo(val path: String) : UIEvent()
}
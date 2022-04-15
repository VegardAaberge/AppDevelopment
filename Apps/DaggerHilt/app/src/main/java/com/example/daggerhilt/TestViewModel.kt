package com.example.daggerhilt

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TestViewModel @Inject constructor(
    @Named("String2") val testString2: String
) : ViewModel(){
    init {
        Log.d("ViewModel", "Test string from ViewModel: $testString2")
    }
}
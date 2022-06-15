package com.example.flowtransformations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModel() {

    private val isAuthenticatedFlow = MutableStateFlow(true)
    private val userFlow = MutableStateFlow<User?>(null)
    private val postsFlow = MutableStateFlow(emptyList<Post>())

    private val _profileState = MutableStateFlow<ProfileState?>(null)
    val profileState = _profileState.asStateFlow()
    var numberString by mutableStateOf("")
        private set

    private val flow1 = (1..10).asFlow().onEach { delay(1000) }
    private val flow2 = (1..20).asFlow().onEach { delay(300) }

    init {
        // Don't wait
        isAuthenticatedFlow.combine(userFlow) { isAuthenticated, user ->
            if(isAuthenticated) user else null
        }.combine(postsFlow){ user, posts ->
            user?.let {
                _profileState.value = profileState.value?.copy(
                    profilePicUrl = user.profilePicUrl,
                    username = user.username,
                    description = user.description,
                    posts = posts
                )
            }
        }.launchIn(viewModelScope)

        // Wait till both are done
        //flow1.zip(flow2) { number1, number2 ->
            //numberString +="($number1 $number2)\n"
        //}.launchIn(viewModelScope)

        merge(flow1, flow2).onEach {
            numberString += "$it\n"
        }.launchIn(viewModelScope)
    }
}
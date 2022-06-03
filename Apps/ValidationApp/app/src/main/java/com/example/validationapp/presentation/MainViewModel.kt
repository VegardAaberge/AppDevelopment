package com.example.validationapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.validationapp.doman.use_case.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validate: ValidateUseCases = ValidateUseCases(
        email =  ValidateEmail(),
        password =  ValidatePassword(),
        repeatedPassword =  ValidateRepeatedPassword(),
        terms =  ValidateTerms(),
    )
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent){
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.AcceptTerms -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validate.email.execute(state.email)
        val passwordResult = validate.password.execute(state.password)
        val repeatedPasswordResult = validate.repeatedPassword.execute(state.password, state.repeatPassword)
        val termsResult = validate.terms.execute(state.acceptedTerms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.successful }

        if(hasError){
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatPasswordError = repeatedPasswordResult.errorMessage,
                termsError = termsResult.errorMessage
            )
        }else{
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}
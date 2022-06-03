package com.example.validationapp.doman.use_case

import android.util.Patterns
import java.util.regex.Pattern

class ValidateEmail {

    fun execute(email: String) : ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(true)
    }
}
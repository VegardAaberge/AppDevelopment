package com.example.validationapp.doman.use_case

import android.util.Patterns
import java.util.regex.Pattern

class ValidatePassword {

    fun execute(password: String) : ValidationResult {
        if(password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val constainsLetterAndDigits = password.any { it.isDigit() } && password.any{ it.isLetter() }
        if(!constainsLetterAndDigits){
            return ValidationResult(
                successful = false,
                errorMessage = "The password need to contain one letter and digit"
            )
        }
        return ValidationResult(true)
    }
}
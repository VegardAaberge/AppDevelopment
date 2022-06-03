package com.example.validationapp.doman.use_case

import android.util.Patterns
import java.util.regex.Pattern

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPasword: String) : ValidationResult {
        if(password != repeatedPasword){
            return ValidationResult(
                successful = false,
                errorMessage = "The password don't match"
            )
        }
        return ValidationResult(true)
    }
}
package com.example.validationapp.doman.use_case

import android.util.Patterns
import java.util.regex.Pattern

class ValidateTerms {

    fun execute(acceptedTerms: Boolean) : ValidationResult {
        if(!acceptedTerms){
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }
        return ValidationResult(true)
    }
}
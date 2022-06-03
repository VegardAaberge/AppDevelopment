package com.example.validationapp.doman.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

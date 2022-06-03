package com.example.validationapp.doman.use_case

data class ValidateUseCases(
    val email: ValidateEmail,
    val password: ValidatePassword,
    val repeatedPassword: ValidateRepeatedPassword,
    val terms: ValidateTerms,
)

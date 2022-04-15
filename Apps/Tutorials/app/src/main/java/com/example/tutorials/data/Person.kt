package com.example.tutorials.data

import java.io.Serializable

data class Person (
    val name: String,
    val age: Int,
    val country: String
) : Serializable
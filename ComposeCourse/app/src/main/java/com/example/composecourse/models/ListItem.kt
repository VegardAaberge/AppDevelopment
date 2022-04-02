package com.example.composecourse.models

data class ListItem(
    val title: String,
    val description: String = "",
    val isSelected: Boolean = false
)
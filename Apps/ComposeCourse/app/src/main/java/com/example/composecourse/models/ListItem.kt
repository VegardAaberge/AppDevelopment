package com.example.composecourse.models

import com.ramcosta.composedestinations.spec.Direction

data class ListItem(
    val title: String,
    val description: String = "",
    val isSelected: Boolean = false,
    val typedDestination: Direction? = null,
)
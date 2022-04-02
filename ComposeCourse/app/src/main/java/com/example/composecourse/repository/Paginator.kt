package com.example.composecourse.repository

interface Paginator<Key, Item> {
    suspend fun loadNextItem()
    fun reset()
}
package com.example.composecourse.repository

interface Paginator<Key, Item> {
    fun loadNextItem()
    fun reset()
}
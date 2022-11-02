package com.example.noteappkmm.di

import com.example.noteappkmm.data.local.DatabaseDriverFactory
import com.example.noteappkmm.data.note.SqlDelightNoteDataSource
import com.example.noteappkmm.domain.note.NoteDataSource
import com.plcoding.noteappkmm.database.NoteDatabase

class DatabaseModule {
    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}
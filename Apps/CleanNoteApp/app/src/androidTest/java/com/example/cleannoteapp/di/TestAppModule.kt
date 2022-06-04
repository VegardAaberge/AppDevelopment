package com.example.cleannoteapp.di

import android.app.Application
import androidx.room.Room
import com.example.cleannoteapp.feature_note.data.data_source.NoteDatabase
import com.example.cleannoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.cleannoteapp.feature_note.domain.repository.NoteRepository
import com.example.cleannoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application) : NoteDatabase{
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase) : NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) : NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNode = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}
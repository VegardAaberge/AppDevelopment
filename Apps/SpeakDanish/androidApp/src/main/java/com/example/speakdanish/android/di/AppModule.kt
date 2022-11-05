package com.example.speakdanish.android.di

import android.app.Application
import com.example.speakdanish.data.local.DatabaseDriverFactory
import com.example.speakdanish.data.SqlDelightRecordingDataSource
import com.example.speakdanish.domain.RecordingDataSource
import com.plcoding.speakdanish.database.RecordingDatabase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application) : SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideRecordingDataSource(driver: SqlDriver) : RecordingDataSource {
        return SqlDelightRecordingDataSource(RecordingDatabase(driver))
    }
}
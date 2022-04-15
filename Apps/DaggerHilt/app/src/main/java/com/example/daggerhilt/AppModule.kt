package com.example.daggerhilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

// One or multiple
// Container for dependencies
// Live as long as the app does, such as Retrofit, Room (SingletonComponent)
// Can also have main activity module, when switch activity then it can't be used anymore

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // blueprint, how to construct the dependencies we want to inject

    @Singleton // Make sure it reuse the old instance
    @Provides // Tell to provide dependency
    @Named("String1")
    fun provideTestString1() = "First String"

    @Singleton
    @Provides
    @Named("StringA")
    fun provideTestString2() = "Second String"
}
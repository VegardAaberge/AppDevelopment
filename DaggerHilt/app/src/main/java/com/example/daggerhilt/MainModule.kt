package com.example.daggerhilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

// One or multiple
// Container for dependencies
// Live as long as the app does, such as Retrofit, Room (SingletonComponent)
// Can also have main activity module, when switch activity then it can't be used anymore

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {
    // blueprint, how to construct the dependencies we want to inject

    @ViewModelScoped
    @Provides
    @Named("String2")
    fun provideTestString2(
        @ApplicationContext context: Context,
        @Named("String1") testString1: String
    ) = "${context.getString(R.string.string_to_inject)} - $testString1"
}
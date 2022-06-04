package com.example.cleannoteapp.feature_note.presentation.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.cleannoteapp.di.AppModule
import com.example.cleannoteapp.feature_note.presentation.MainActivity
import com.example.cleannoteapp.feature_note.presentation.NavGraphs
import com.example.cleannoteapp.ui.theme.CleanNoteAppTheme
import com.google.common.truth.Truth
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            CleanNoteAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible(){
        //TODO get it to work with romcosta navigation
    }
}

@Destination(start = true)
@Composable
fun StartScreen(
    navigator: DestinationsNavigator
) {
    NotesScreen(navigator = navigator)
}
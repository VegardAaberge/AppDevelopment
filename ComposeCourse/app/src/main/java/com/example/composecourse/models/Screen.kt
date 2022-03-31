package com.example.composecourse.models

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object SplashScreen : Screen("splash_screen")

    object HomeFragment : Screen("home")
    object ChatFragment : Screen("chat")
    object SettingsFragment : Screen("settings")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}

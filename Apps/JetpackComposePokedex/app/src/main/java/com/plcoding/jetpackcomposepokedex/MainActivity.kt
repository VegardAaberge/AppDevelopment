package com.plcoding.jetpackcomposepokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.jetpackcomposepokedex.pokemondetail.PokemonDetailScreen
import com.plcoding.jetpackcomposepokedex.pokemonlist.PokemonListScreen
import com.plcoding.jetpackcomposepokedex.ui.theme.JetpackComposePokedexTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePokedexTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator) {
    PokemonListScreen(navigator)
}

@Destination()
@Composable
fun DetailScreen(
    dominantColor: Int,
    pokemonName : String,
    navigator: DestinationsNavigator
) {
    PokemonDetailScreen(
        dominantColor = Color(dominantColor),
        pokemonName = pokemonName?.lowercase() ?: "",
        navigator = navigator
    )
}

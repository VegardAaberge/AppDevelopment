package com.example.composecourse.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composecourse.models.Screen
import com.example.composecourse.navigation.destinations.DetailScreenDestination
import com.plcoding.composenavdestinationsdemo.User
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDateTime

@Composable
fun SetupNavigationV2() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}

@Destination(start = true)
@Composable
fun MainScreen(
    navigatior: DestinationsNavigator
) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navigatior.navigate(
                    DetailScreenDestination(
                        User(
                            name = text,
                            id = "321",
                            created = LocalDateTime.now()
                        )
                    )
                )
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "To Detail Screen")
        }
    }
}

@Destination
@Composable
fun DetailScreen(
    navigatior: DestinationsNavigator,
    user: User
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Hello ${user.name} with ID ${user.id} created ${user.created} ")
    }
}
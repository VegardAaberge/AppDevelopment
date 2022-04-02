package com.example.composecourse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composecourse.models.BottomNavItem
import com.example.composecourse.models.Screen
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun BottomNavigationScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = Screen.HomeFragment.route,
                        icon = Icons.Default.Home,
                    ),
                    BottomNavItem(
                        name = "Chat",
                        route = Screen.ChatFragment.route,
                        icon = Icons.Default.Notifications,
                        badgeCount = 23,
                    ),
                    BottomNavItem(
                        name = "Settings",
                        route = Screen.SettingsFragment.route,
                        icon = Icons.Default.Settings,
                        badgeCount = 2143
                    ),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) {
        BottomNavigationScreen(navController = navController)
    }
}

@Composable
private fun BottomNavigationScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable(Screen.HomeFragment.route){
            HomeScreen()
        }
        composable(Screen.ChatFragment.route){
            ChatScreen()
        }
        composable(Screen.SettingsFragment.route){
            SettingsScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(item.badgeCount > 0){
                            BadgedBox(
                                badge = {
                                    Badge(
                                        backgroundColor = Color.Red,
                                    ){
                                        Text(
                                            color = Color.White,
                                            text = item.badgeCount.toString()
                                        )
                                    }
                                })
                            {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        }else{
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if(selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home screen")
    }
}

@Composable
private fun ChatScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat screen")
    }
}

@Composable
private fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings screen")
    }
}
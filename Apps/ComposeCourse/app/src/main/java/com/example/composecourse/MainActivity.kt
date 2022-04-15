package com.example.composecourse

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecourse.destinations.*
import com.example.composecourse.models.ListItem
import com.example.composecourse.navigation.PostScreen
import com.example.composecourse.ui.theme.ComposeCourseTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeCourseTheme{
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

@Destination(start = true)
@Composable
fun ScreenList(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    var listItems by remember {
        mutableStateOf(
            mutableListOf(
                ListItem(
                    title = "AnimationBox",
                    description = "Click me box that can expand",
                    typedDestination = AnimationBoxScreenDestination()
                ),
                ListItem(
                    title = "BottomNavigation",
                    description = "Bottom Navigation Bar with 3 items",
                    typedDestination = BottomNavigationScreenDestination()
                ),
                ListItem(
                    title = "Canvas",
                    description = "",
                    typedDestination = MyCanvasDestination()
                ),
                ListItem(
                    title = "CircularProgressBar",
                    description = "Loads a circular progress bar",
                    typedDestination = CircularProgressBarScreenDestination(),
                ),
                ListItem(
                    title = "ColorState",
                    description = "Change color of the bottom box by tapping top",
                    typedDestination = ColorStateScreenDestination(),
                ),
                ListItem(
                    title = "ConstraintsBox",
                    description = "Constrain two boxes",
                    typedDestination = ConstraintBoxScreenDestination(),
                ),
                ListItem(
                    title = "CustomText",
                    description = "Text with different styles",
                    typedDestination = CustomTextScreenDestination(),
                ),
                ListItem(
                    title = "ImageCard",
                    description = "Pretty image card",
                    typedDestination = ImageCardScreenDestination(),
                ),
                ListItem(
                    title = "LazyColumnList",
                    description = "Basic list",
                    typedDestination = LazyColumnListScreenDestination(),
                ),
                ListItem(
                    title = "LazyColumnSelected",
                    description = "Multiselection list",
                    typedDestination = LazyColumnSelectedScreenDestination(),
                ),
                ListItem(
                    title = "MusicKnob",
                    description = "Music knob that lower and increase sound of a song",
                    typedDestination = MusicKnobScreenDestination(),
                ),
                ListItem(
                    title = "Navigation",
                    description = "Old way of navigation",
                    typedDestination = NavigationScreenDestination(),
                ),
                ListItem(
                    title = "NavigationV2",
                    description = "Navigation using raamcosta navigation plugin",
                    typedDestination = NavigationV2ScreenDestination(),
                ),
                ListItem(
                    title = "Pagination",
                    description = "Simulate network call and add new items at each 20 items",
                    typedDestination = PaginationScreenDestination(),
                ),
                ListItem(
                    title = "Permissions",
                    description = "Ask for 2 permissions and display",
                    typedDestination = PermissionsScreenDestination(),
                ),
                ListItem(
                    title = "ProfileScreen",
                    description = "Instagram profile screen",
                    typedDestination = ProfileScreenDestination(),
                ),
                ListItem(
                    title = "ScreenSizes",
                    description = "List display different on wide and compact devices",
                    typedDestination = ScreenSizeScreenDestination(),
                ),
                ListItem(
                    title = "SideEffects",
                    description = "Button that use side effects",
                    typedDestination = SideEffectsScreenDestination(),
                ),
                ListItem(
                    title = "SplashScreen",
                    description = "Show a animated picture before opening up the screen",
                    typedDestination = SplashScreenDestination(),
                ),
                ListItem(
                    title = "TextFieldSnackbar",
                    description = "Show a snackbar popup",
                    typedDestination = TextFieldSnackbarScteenDestination(),
                )
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(listItems.size){ i ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navigator.navigate(listItems[i].typedDestination!!)
                    }
            ) {
                Text(
                    text = listItems[i].title,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = listItems[i].description)
            }
        }
    }
}








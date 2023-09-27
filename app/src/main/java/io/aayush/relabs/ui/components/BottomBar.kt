package io.aayush.relabs.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.aayush.relabs.ui.navigation.Screen

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.ThreadPreview,
        Screen.News,
        Screen.Alerts,
        Screen.Settings
    )
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    when (navBackStackEntry?.destination?.route) {
        Screen.ThreadPreview.route, Screen.News.route, Screen.Alerts.route, Screen.Settings.route -> {
            bottomBarState.value = true
        }

        else -> bottomBarState.value = false
    }

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar {
            screens.forEachIndexed { _, item ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true,
                    label = { Text(text = stringResource(id = item.title)) },
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Image(painter = painterResource(id = item.icon), contentDescription = "")
                    }
                )
            }
        }
    }
}

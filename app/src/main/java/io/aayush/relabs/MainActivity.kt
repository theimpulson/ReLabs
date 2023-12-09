/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.util.fastAny
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.aayush.relabs.ui.components.BottomBar
import io.aayush.relabs.ui.components.rememberSystemUiController
import io.aayush.relabs.ui.navigation.Screen
import io.aayush.relabs.ui.navigation.SetupNavGraph
import io.aayush.relabs.ui.theme.ReLabsTheme
import io.aayush.relabs.utils.CommonModule.ACCESS_TOKEN
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        val topRoutes: List<Screen> = listOf(
            Screen.ThreadPreview,
            Screen.News,
            Screen.ForumPreview,
            Screen.Alerts
//            Screen.More
        )
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""

        setContent {
            ReLabsTheme {
                val systemUiController = rememberSystemUiController()
                val navController = rememberNavController()

                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute by remember {
                    derivedStateOf {
                        currentBackStackEntry?.destination?.route ?: Screen.ThreadPreview.route
                    }
                }

                val darkTheme = isSystemInDarkTheme()
                val colorScheme = MaterialTheme.colorScheme

                Scaffold(bottomBar = { BottomBar(navController = navController) }) {
                    SetupNavGraph(
                        navHostController = navController,
                        paddingValues = it,
                        if (accessToken.isBlank()) Screen.Login.route else Screen.ThreadPreview.route
                    )
                }

                LaunchedEffect(key1 = currentRoute) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                        val navigationBarElevation = NavigationBarDefaults.Elevation
                        val navigationBarColor =
                            if (topRoutes.fastAny { it.route == currentRoute }) {
                                colorScheme.surfaceColorAtElevation(navigationBarElevation)
                            } else {
                                colorScheme.background
                            }
                        systemUiController.setNavigationBarColor(navigationBarColor, !darkTheme)
                    }
                }
            }
        }
    }
}

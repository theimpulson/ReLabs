/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.aayush.relabs.ui.screens.alerts.AlertsScreen
import io.aayush.relabs.ui.screens.home.HomeScreen
import io.aayush.relabs.ui.screens.login.LoginScreen
import io.aayush.relabs.ui.screens.news.NewsScreen
import io.aayush.relabs.ui.screens.settings.SettingsScreen
import io.aayush.relabs.ui.screens.thread.ThreadScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    startDestinationRoute: String = Screen.Login.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestinationRoute,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.Login.route) { LoginScreen(navHostController) }
        composable(route = Screen.Home.route) { HomeScreen(navHostController) }
        composable(route = Screen.Alerts.route) { AlertsScreen(navHostController) }
        composable(route = Screen.News.route) { NewsScreen(navHostController) }
        composable(route = Screen.Settings.route) { SettingsScreen(navHostController) }
        composable(
            route = Screen.Thread.route,
            arguments = listOf(
                navArgument(NavArg.THREAD_ID.name) {
                    type = NavType.IntType
                }
            )
        ) {
            ThreadScreen(navHostController, it.arguments!!.getInt(NavArg.THREAD_ID.name))
        }
    }
}

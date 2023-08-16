/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.aayush.relabs.ui.screens.home.HomeScreen
import io.aayush.relabs.ui.screens.login.LoginScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController)
        }
    }
}

/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen(route = "login_screen")
    data object Home : Screen(route = "home_screen")
}

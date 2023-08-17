/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Login : Screen(route = "login_screen", title = "Login", icon = Icons.Rounded.Login)
    data object Home : Screen(route = "home_screen", title = "Home", icon = Icons.Rounded.Home)
    data object News : Screen(route = "news_screen", title = "News", icon = Icons.Rounded.Newspaper)
    data object Alerts :
        Screen(route = "alerts_screen", title = "Alerts", icon = Icons.Rounded.Notifications)
    data object Settings :
        Screen(route = "settings_screen", title = "Settings", icon = Icons.Rounded.Settings)
}

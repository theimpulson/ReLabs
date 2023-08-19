/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

import androidx.annotation.DrawableRes
import io.aayush.relabs.R

sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int) {
    data object Login : Screen(route = "login_screen", title = "Login", icon = R.drawable.ic_login)
    data object Home : Screen(route = "home_screen", title = "Home", icon = R.drawable.ic_forum)
    data object News : Screen(route = "news_screen", title = "News", icon = R.drawable.ic_news)
    data object Alerts :
        Screen(route = "alerts_screen", title = "Alerts", icon = R.drawable.ic_notifications)
    data object Settings :
        Screen(route = "settings_screen", title = "Settings", icon = R.drawable.ic_more)
}

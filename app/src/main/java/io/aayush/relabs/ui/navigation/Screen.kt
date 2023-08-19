/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.aayush.relabs.R

sealed class Screen(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    data object Login : Screen(
        route = "login_screen",
        title = R.string.login,
        icon = R.drawable.ic_login
    )

    data object Home : Screen(
        route = "home_screen",
        title = R.string.home,
        icon = R.drawable.ic_forum
    )

    data object News : Screen(
        route = "news_screen",
        title = R.string.news,
        icon = R.drawable.ic_news
    )

    data object Alerts : Screen(
        route = "alerts_screen",
        title = R.string.alerts,
        icon = R.drawable.ic_notifications
    )

    data object Settings : Screen(
        route = "settings_screen",
        title = R.string.settings,
        icon = R.drawable.ic_more
    )
}

/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.navigation

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.aayush.relabs.R

sealed class Screen(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    data object Login : Screen(
        route = "login_screen",
        title = R.string.login,
        icon = R.drawable.ic_login
    )

    data object ThreadPreview : Screen(
        route = "thread_preview_screen",
        title = R.string.thread_preview,
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

    data object More : Screen(
        route = "more_screen",
        title = R.string.more,
        icon = R.drawable.ic_more
    )

    data object Thread : Screen(
        route = "thread_screen/{${NavArg.THREAD_ID.name}}",
        title = R.string.thread,
        icon = R.drawable.ic_forum
    ) {
        fun withID(id: Int): String {
            return this.route.replace("{${NavArg.THREAD_ID.name}}", id.toString())
        }
    }

    data object Reply : Screen(
        route = "reply_screen/{${NavArg.THREAD_ID.name}}",
        title = R.string.reply,
        icon = R.drawable.ic_quick_reply
    ) {
        fun withID(id: Int): String {
            return this.route.replace("{${NavArg.THREAD_ID.name}}", id.toString())
        }
    }

    data object Node : Screen(
        route = "node_screen/{${NavArg.NODE_ID.name}}/{${NavArg.NODE_TITLE.name}}=",
        title = R.string.node,
        icon = R.drawable.ic_phone
    ) {
        fun withIDAndTitle(id: Int, title: String): String {
            return this.route.replace("{${NavArg.NODE_ID.name}}", id.toString())
                .replace("{${NavArg.NODE_TITLE.name}}", Uri.encode(title))
        }
    }

    data object NodePreview : Screen(
        route = "node_preview_screen",
        title = R.string.forum_preview,
        icon = R.drawable.ic_phone
    )
}

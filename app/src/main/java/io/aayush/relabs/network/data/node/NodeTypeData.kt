package io.aayush.relabs.network.data.node

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.Avatar
import io.aayush.relabs.network.data.common.DateTime

@JsonClass(generateAdapter = true)
data class NodeTypeData(
    val canCreateThread: Boolean = false,
    val canIgnore: Boolean = false,
    val discussion_count: Int = 0,
    val isIgnoredOnForumList: Boolean = false,
    val isIgnoredOnNewsFeed: Boolean = false,
    val isIgnoredOnThreadList: Boolean = false,
    val isUnread: Boolean = false,
    val isWatched: Boolean = false,
    val last_post_date: DateTime = DateTime(),
    val last_post_id: Int = 0,
    val last_post_user_avatar: Avatar = Avatar(),
    val last_post_user_id: Int = 0,
    val last_post_username: String = String(),
    val last_thread_id: Int = 0,
    val last_thread_permalink: String = String(),
    val last_thread_title: String = String(),
    val message_count: Int = 0
)

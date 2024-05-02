package io.aayush.relabs.network.data.node

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NodeData(
    val discussion_count: Int = 0,
    val isUnread: Boolean = false,
    val message_count: Int = 0
)

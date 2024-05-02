package io.aayush.relabs.network.data.thread

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.Pagination
import io.aayush.relabs.network.data.post.Post

@JsonClass(generateAdapter = true)
data class ThreadInfo(
    val first_unread: Post = Post(),
    val pagination: Pagination = Pagination(),
    val posts: List<Post> = emptyList(),
    val thread: Thread = Thread()
)

package io.aayush.relabs.network.data.thread

import io.aayush.relabs.network.data.common.Pagination
import io.aayush.relabs.network.data.post.Post

data class ThreadInfo(
    val pagination: Pagination = Pagination(),
    val posts: List<Post> = emptyList(),
    val thread: Thread = Thread()
)

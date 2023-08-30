package io.aayush.relabs.network.data.post

data class PostReply(
    val success: Boolean = false,
    val post: Post = Post()
)

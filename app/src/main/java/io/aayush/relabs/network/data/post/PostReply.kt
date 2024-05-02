package io.aayush.relabs.network.data.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostReply(
    val success: Boolean = false,
    val post: Post = Post()
)

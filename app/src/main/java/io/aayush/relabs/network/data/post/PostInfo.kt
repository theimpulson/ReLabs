package io.aayush.relabs.network.data.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostInfo(
    val post: Post = Post()
)

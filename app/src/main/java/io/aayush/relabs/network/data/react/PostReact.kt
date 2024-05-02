package io.aayush.relabs.network.data.react

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostReact(
    val success: Boolean = false,
    val action: String = String()
)

package io.aayush.relabs.network.data.thread

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Choice(
    val response: String = String(),
    val vote_count: Int? = null
)

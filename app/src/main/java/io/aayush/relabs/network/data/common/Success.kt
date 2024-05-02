package io.aayush.relabs.network.data.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Success(
    val success: Boolean = false,
)

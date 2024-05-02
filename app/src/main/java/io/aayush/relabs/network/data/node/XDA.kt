package io.aayush.relabs.network.data.node

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XDA(
    val coverUrl: String? = String(),
    val iconUrl: String? = String(),
    val isDevice: Boolean = false
)

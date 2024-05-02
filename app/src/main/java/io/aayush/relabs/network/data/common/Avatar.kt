package io.aayush.relabs.network.data.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Avatar(
    val data: Data = Data(),
    val type: String = String()
)

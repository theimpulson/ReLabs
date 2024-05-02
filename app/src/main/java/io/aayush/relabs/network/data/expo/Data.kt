package io.aayush.relabs.network.data.expo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val expoPushToken: String = String(),
)

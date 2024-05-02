package io.aayush.relabs.network.data.expo

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class ExpoData(
    val deviceToken: String,
    val deviceId: String = UUID.randomUUID().toString(),
    val development: Boolean = false,
    val appId: String = "com.xda.labs.play",
    val projectId: String = "a5ad53e5-6e27-4708-a3f0-a09077da708f",
    val type: String = "fcm",
)

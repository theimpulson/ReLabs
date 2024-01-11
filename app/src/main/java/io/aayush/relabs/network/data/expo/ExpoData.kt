package io.aayush.relabs.network.data.expo

import java.util.UUID

data class ExpoData(
    val deviceToken: String,
    val deviceId: String = UUID.randomUUID().toString(),
    val development: Boolean = false,
    val appId: String = "com.xda.labs.play",
    val projectId: String = "a5ad53e5-6e27-4708-a3f0-a09077da708f",
    val type: String = "fcm",
)

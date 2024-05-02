package io.aayush.relabs.network.data.expo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostData(
    val data: Data = Data(),
)

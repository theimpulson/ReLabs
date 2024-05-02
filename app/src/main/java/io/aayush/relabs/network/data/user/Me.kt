package io.aayush.relabs.network.data.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Me(
    val me: User? = User()
)

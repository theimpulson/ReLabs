package io.aayush.relabs.network.data.react

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sprite(
    val bs: String = String(),
    val h: String = String(),
    val w: String = String(),
    val x: String = String(),
    val y: String = String()
)

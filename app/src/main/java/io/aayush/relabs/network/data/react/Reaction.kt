package io.aayush.relabs.network.data.react

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reaction(
    val color: String = String(),
    val id: Int = 0,
    val image: Image = Image(),
    val score: Int = 0,
    val sprite: Sprite = Sprite(),
    val title: String = String()
)

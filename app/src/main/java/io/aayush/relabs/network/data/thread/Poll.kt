package io.aayush.relabs.network.data.thread

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Poll(
    val choices: List<Choice> = listOf(),
    val id: Int = 0,
    val question: String = String()
)

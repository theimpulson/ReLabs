package io.aayush.relabs.network.data.thread

data class Poll(
    val choices: List<Choice> = listOf(),
    val id: Int = 0,
    val question: String = String()
)

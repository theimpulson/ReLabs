package io.aayush.relabs.network.data.node

data class Discussion(
    val allow_answer_downvote: Boolean = false,
    val allow_answer_voting: Boolean = false,
    val allowed_thread_types: List<String> = emptyList()
)

package io.aayush.relabs.network.data.react

data class ReactionCount(
    val count: Int = 0,
    val reaction: Reaction = Reaction(),
    val reaction_id: Int = 0
)

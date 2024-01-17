package io.aayush.relabs.network.data.react

data class ReactionUser(
    val reaction: Reaction = Reaction(),
    val reaction_id: Int = 0,
    val user_id: Int = 0,
    val username: String = String()
)

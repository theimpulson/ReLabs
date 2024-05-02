package io.aayush.relabs.network.data.react

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReactionUser(
    val reaction: Reaction = Reaction(),
    val reaction_id: Int = 0,
    val user_id: Int = 0,
    val username: String = String()
)

package io.aayush.relabs.network.data.conversation

import io.aayush.relabs.network.data.common.Pagination

data class Conversations(
    val conversations: List<Conversation> = emptyList(),
    val pagination: Pagination = Pagination()
)

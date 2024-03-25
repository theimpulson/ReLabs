package io.aayush.relabs.network.data.search

import io.aayush.relabs.network.data.common.DateTime

data class Search(
    val created_at: DateTime = DateTime(),
    val id: Int = 0,
    val result_count: Int = 0,
    val search_constraints: List<String> = emptyList(),
    val search_order: String = String(),
    val search_type: String = String()
)

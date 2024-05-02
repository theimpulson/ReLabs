package io.aayush.relabs.network.data.search

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.DateTime

@JsonClass(generateAdapter = true)
data class Search(
    val created_at: DateTime = DateTime(),
    val id: Int = 0,
    val result_count: Int = 0,
    val search_constraints: List<String> = emptyList(),
    val search_order: String = String(),
    val search_type: String = String()
)

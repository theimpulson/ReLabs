package io.aayush.relabs.network.data.search

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.Pagination
import io.aayush.relabs.network.data.node.Node

@JsonClass(generateAdapter = true)
data class SearchResultNode(
    val search: Search = Search(),
    val results: List<Node> = emptyList(),
    val pagination: Pagination = Pagination()
)

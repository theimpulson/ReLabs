package io.aayush.relabs.network.data.search

import io.aayush.relabs.network.data.common.Pagination
import io.aayush.relabs.network.data.node.Node

data class SearchResultNode(
    val search: Search = Search(),
    val results: List<Node> = emptyList(),
    val pagination: Pagination = Pagination()
)

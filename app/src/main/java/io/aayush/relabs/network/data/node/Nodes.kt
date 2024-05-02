package io.aayush.relabs.network.data.node

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Nodes(
    val nodes: List<Node> = emptyList()
)

package io.aayush.relabs.network.data.node

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Breadcrumb(
    val depth: Int = 0,
    val lft: Int = 0,
    val node_id: Int = 0,
    val node_name: Any? = Any(),
    val node_type_id: String = String(),
    val title: String = String()
)

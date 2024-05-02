package io.aayush.relabs.network.data.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    val current_page: Int = 0,
    val last_page: Int = 0,
    val per_page: Int = 0,
    val shown: Int = 0,
    val total: Int = 0
)

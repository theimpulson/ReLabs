package io.aayush.relabs.network.data.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostSearch(
    val search: Search = Search()
)

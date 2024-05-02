package io.aayush.relabs.network.data.search

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.Pagination
import io.aayush.relabs.network.data.thread.Thread

@JsonClass(generateAdapter = true)
data class SearchResultThread(
    val search: Search = Search(),
    val results: List<Thread> = emptyList(),
    val pagination: Pagination = Pagination()
)

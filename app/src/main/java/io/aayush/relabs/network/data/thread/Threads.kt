package io.aayush.relabs.network.data.thread

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.Pagination

@JsonClass(generateAdapter = true)
data class Threads(
    val pagination: Pagination = Pagination(),
    val threads: List<Thread> = listOf(),
    val sticky: List<Thread> = listOf()
)

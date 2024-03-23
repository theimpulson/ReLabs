package io.aayush.relabs.network.data.thread

import io.aayush.relabs.network.data.common.Pagination

data class Threads(
    val pagination: Pagination = Pagination(),
    val threads: List<Thread> = listOf(),
    val sticky: List<Thread> = listOf()
)

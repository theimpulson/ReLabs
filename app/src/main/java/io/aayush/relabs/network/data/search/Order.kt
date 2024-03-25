package io.aayush.relabs.network.data.search

enum class Order(val value: String) {
    RELEVANCE("relevance"),
    DATE("date"),
    MOST_RECENT("last_update"),
    MOST_REPLIES("replies")
}

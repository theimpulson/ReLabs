package io.aayush.relabs.network.data.thread

import com.squareup.moshi.Json

enum class DiscussionState {
    @Json(name = "visible")
    VISIBLE,

    @Json(name = "moderated")
    MODERATED,

    @Json(name = "deleted")
    DELETED
}

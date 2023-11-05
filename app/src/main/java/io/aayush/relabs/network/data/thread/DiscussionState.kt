package io.aayush.relabs.network.data.thread

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class DiscussionState {
    @Json(name = "visible")
    VISIBLE,

    @Json(name = "moderated")
    MODERATED,

    @Json(name = "deleted")
    DELETED
}

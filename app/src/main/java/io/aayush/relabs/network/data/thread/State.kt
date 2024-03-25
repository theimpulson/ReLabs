package io.aayush.relabs.network.data.thread

import com.squareup.moshi.Json

enum class State {

    @Json(name = "visible")
    VISIBLE,

    @Json(name = "deleted")
    DELETED,

    @Json(name = "moderated")
    MODERATED
}
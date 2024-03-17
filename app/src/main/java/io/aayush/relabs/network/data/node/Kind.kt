package io.aayush.relabs.network.data.node

import com.squareup.moshi.Json

enum class Kind {
    @Json(name = "Forum") // Xenforo
    FORUM,

    @Json(name = "Category") // vBulletin
    CATEGORY
}

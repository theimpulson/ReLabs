package io.aayush.relabs.network.data.thread

import com.squareup.moshi.Json

enum class Prefix {

    @Json(name = "General")
    GENERAL,

    @Json(name = "Question")
    QUESTION,

    @Json(name = "Development")
    DEVELOPMENT,

    @Json(name =  "How To Guide")
    HOW_TO_GUIDE,

    @Json(name = "Themes / Apps / Mods")
    THEMES_APPS_MODS,

    @Json(name = "Accessories")
    ACCESSORIES,

    @Json(name = "[Resolved]")
    RESOLVED,

    @Json(name = "[Closed]")
    CLOSED,

    // Default
    UNAVAILABLE
}

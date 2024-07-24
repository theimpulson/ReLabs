package io.aayush.relabs.network.data.alert

import com.squareup.moshi.Json

enum class ContentType {
    @Json(name = "adav_achievement")
    ACHIEVEMENT,

    @Json(name = "post")
    POST,

    @Json(name = "user")
    USER,

    @Json(name = "adlb_leaderboard")
    LEADERBOARD,

    @Json(name = "conversation_message")
    MESSAGE
}

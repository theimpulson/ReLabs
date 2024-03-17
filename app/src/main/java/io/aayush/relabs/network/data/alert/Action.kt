package io.aayush.relabs.network.data.alert

import com.squareup.moshi.Json

enum class Action {
    @Json(name = "reaction")
    REACTION,

    @Json(name = "award")
    AWARD,

    @Json(name = "insert")
    INSERT,

    @Json(name = "following")
    FOLLOWING,

    @Json(name = "report_resolved")
    REPORT_RESOLVED
}

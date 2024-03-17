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
    REPORT_RESOLVED,

    @Json(name = "report_rejected")
    REPORT_REJECTED,

    @Json(name = "quote")
    QUOTE,

    @Json(name = "post_delete")
    POST_DELETED,

    @Json(name = "post_edit")
    POST_EDITED
}

package io.aayush.relabs.network.data.alert

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.Pagination

@JsonClass(generateAdapter = true)
data class Alerts(
    val alerts: List<UserAlert> = emptyList(),
    val pagination: Pagination = Pagination()
)

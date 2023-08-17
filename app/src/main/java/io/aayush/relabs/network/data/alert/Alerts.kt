package io.aayush.relabs.network.data.alert

import io.aayush.relabs.network.data.common.Pagination

data class Alerts(
    val alerts: List<UserAlert> = emptyList(),
    val pagination: Pagination = Pagination()
)

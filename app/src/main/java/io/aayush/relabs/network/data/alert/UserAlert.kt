package io.aayush.relabs.network.data.alert

import io.aayush.relabs.network.data.user.User

data class UserAlert(
    val User: User = User(),
    val action: String = String(),
    val alert_id: Int = 0,
    val alert_text: String = String(),
    val alert_url: String = String(),
    val alerted_user_id: Int = 0,
    val auto_read: Boolean = false,
    val content_id: Int = 0,
    val content_type: String = String(),
    val event_date: Int = 0,
    val read_date: Int = 0,
    val user_id: Int = 0,
    val username: String = String(),
    val view_date: Int = 0
)

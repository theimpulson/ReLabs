package io.aayush.relabs.network.data.alert

import io.aayush.relabs.network.data.common.DateTime
import io.aayush.relabs.network.data.user.User

data class UserAlert(
    val action: String = String(),
    val content_id: Int = 0,
    val content_type: String = String(),
    val created_at: DateTime = DateTime(),
    val id: Int = 0,
    val isViewed: Boolean = false,
    val message: String = String(),
    val read_at: DateTime? = DateTime(),
    val url: String = String(),
    val user: User = User(),
    val viewed_at: DateTime = DateTime()
)

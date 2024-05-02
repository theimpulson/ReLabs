package io.aayush.relabs.network.data.alert

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.DateTime
import io.aayush.relabs.network.data.user.User

@JsonClass(generateAdapter = true)
data class UserAlert(
    val action: Action = Action.REACTION,
    val content_id: Int = 0,
    val content_type: ContentType = ContentType.POST,
    val created_at: DateTime = DateTime(),
    val id: Int = 0,
    val isViewed: Boolean = false,
    val message: String = String(),
    val read_at: DateTime? = DateTime(),
    val url: String? = String(),
    val user: User? = User(),
    val viewed_at: DateTime = DateTime()
)

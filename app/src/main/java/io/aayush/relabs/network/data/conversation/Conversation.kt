package io.aayush.relabs.network.data.conversation

import io.aayush.relabs.network.data.user.User

data class Conversation(
    val Starter: User = User(),
    val can_edit: Boolean = false,
    val can_invite: Boolean = false,
    val can_reply: Boolean = false,
    val can_upload_attachment: Boolean = false,
    val conversation_id: Int = 0,
    val conversation_open: Boolean = false,
    val first_message_id: Int = 0,
    val is_starred: Boolean = false,
    val is_unread: Boolean = false,
    val last_message_date: Int = 0,
    val last_message_id: Int = 0,
    val last_message_user_id: Int = 0,
    val open_invite: Boolean = false,
    val recipient_count: Int = 0,
    val recipients: Map<String, String>,
    val reply_count: Int = 0,
    val start_date: Int = 0,
    val title: String = String(),
    val user_id: Int = 0,
    val username: String = String(),
    val view_url: String = String()
)

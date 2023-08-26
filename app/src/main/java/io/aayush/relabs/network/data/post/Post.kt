package io.aayush.relabs.network.data.post

import io.aayush.relabs.network.data.user.User

data class Post(
    val Attachments: List<Attachment> = emptyList(),
    val User: User = User(),
    val attach_count: Int = 0,
    val can_edit: Boolean = false,
    val can_hard_delete: Boolean = false,
    val can_react: Boolean = false,
    val can_soft_delete: Boolean = false,
    val can_view_attachments: Boolean = false,
    val is_first_post: Boolean = false,
    val is_last_post: Boolean = false,
    val is_reacted_to: Boolean = false,
    val is_unread: Boolean = false,
    val last_edit_date: Int = 0,
    val message: String = String(),
    val message_parsed: String = String(),
    val message_state: String = String(),
    val position: Int = 0,
    val post_date: Int = 0,
    val post_id: Int = 0,
    val reaction_score: Int = 0,
    val thread_id: Int = 0,
    val user_id: Int = 0,
    val username: String = String(),
    val view_url: String = String(),
    val warning_message: String = String()
)

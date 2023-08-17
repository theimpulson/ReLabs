package io.aayush.relabs.network.data.node

data class TypeData(
    val allow_posting: Boolean = false,
    val can_create_thread: Boolean = false,
    val can_upload_attachment: Boolean = false,
    val discussion: Discussion = Discussion(),
    val discussion_count: Int = 0,
    val forum_type_id: String = String(),
    val is_unread: Boolean = false,
    val last_post_date: Int = 0,
    val last_post_id: Int = 0,
    val last_post_username: String = String(),
    val last_thread_id: Int = 0,
    val last_thread_prefix_id: Int = 0,
    val last_thread_title: String = String(),
    val message_count: Int = 0,
    val min_tags: Int = 0,
    val require_prefix: Boolean = false
)

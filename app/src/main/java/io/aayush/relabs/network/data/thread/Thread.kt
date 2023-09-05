package io.aayush.relabs.network.data.thread

import io.aayush.relabs.network.data.node.Node
import io.aayush.relabs.network.data.user.CustomFields
import io.aayush.relabs.network.data.user.User

data class Thread(
    val Forum: Node = Node(),
    val User: User? = User(),
    val can_edit: Boolean = false,
    val can_edit_tags: Boolean = false,
    val can_hard_delete: Boolean = false,
    val can_reply: Boolean = false,
    val can_soft_delete: Boolean = false,
    val can_view_attachments: Boolean = false,
    val custom_fields: CustomFields = CustomFields(),
    val discussion_open: Boolean = false,
    val discussion_state: DiscussionState = DiscussionState.MODERATED,
    val discussion_type: String = String(),
    val first_post_id: Int = 0,
    val first_post_reaction_score: Int = 0,
    val highlighted_post_ids: List<Any> = emptyList(),
    val is_first_post_pinned: Boolean = false,
    val is_unread: Boolean = false,
    val is_watching: Boolean = false,
    val last_post_date: Int = 0,
    val last_post_id: Int = 0,
    val last_post_user_id: Int = 0,
    val last_post_username: String = String(),
    val node_id: Int = 0,
    val post_date: Int = 0,
    val prefix: String = String(),
    val prefix_id: Int = 0,
    val reply_count: Int = 0,
    val sticky: Boolean = false,
    val tags: List<String> = emptyList(),
    val thread_id: Int = 0,
    val title: String = String(),
    val user_id: Int = 0,
    val username: String = String(),
    val view_count: Int = 0,
    val view_url: String = String(),
    val visitor_post_count: Int = 0
)

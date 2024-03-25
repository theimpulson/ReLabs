package io.aayush.relabs.network.data.thread

import io.aayush.relabs.network.data.common.DateTime
import io.aayush.relabs.network.data.node.Node
import io.aayush.relabs.network.data.post.Post
import io.aayush.relabs.network.data.user.User

data class Thread(
    val apiVoteUrl: String = String(),
    val canDelete: Boolean = false,
    val canDownvote: Boolean = false,
    val canEdit: Boolean = false,
    val canIgnore: Boolean = false,
    val canReply: Boolean = false,
    val canReport: Boolean = false,
    val canVote: Boolean = false,
    val created_at: DateTime = DateTime(),
    val firstPost: Post = Post(),
    val hasPoll: Boolean = false,
    val id: Int = 0,
    val isIgnoredOnNewsFeed: Boolean = false,
    val isIgnoredOnThreadList: Boolean = false,
    val isParticipating: Boolean = false,
    val isUnread: Boolean = false,
    val isWatched: Boolean = false,
    val is_open: Boolean = false,
    val is_sticky: Boolean = false,
    val last_post_at: DateTime = DateTime(),
    val last_post_id: Int = 0,
    val last_post_user_id: Int = 0,
    val last_post_username: String = String(),
    val node: Node = Node(),
    val permalink: String = String(),
    val prefix: Prefix? = Prefix.UNAVAILABLE,
    val poll: Poll = Poll(),
    val replyUrl: String = String(),
    val reply_count: Int = 0,
    val reportUrl: String = String(),
    val state: State = State.VISIBLE,
    val thread_type: String = String(),
    val title: String = String(),
    val user: User? = User(),
    val userVote: Boolean = false,
    val view_count: Int = 0,
    val voteUrl: String = String(),
    val vote_count: Int = 0,
    val vote_score: Int = 0
)

package io.aayush.relabs.network.data.post

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.common.DateTime
import io.aayush.relabs.network.data.react.ReactionCount
import io.aayush.relabs.network.data.react.ReactionUser
import io.aayush.relabs.network.data.user.User

@JsonClass(generateAdapter = true)
data class Post(
    val apiVoteUrl: String = String(),
    val attach_count: Int = 0,
    val attachments: List<Attachment> = listOf(),
    val canDelete: Boolean = false,
    val canDownvote: Boolean = false,
    val canEdit: Boolean = false,
    val canReact: Boolean = false,
    val canReport: Boolean = false,
    val canVote: Boolean = false,
    val created_at: DateTime = DateTime(),
    val id: Int = 0,
    val isBookmarked: Boolean = false,
    val isReacted: Boolean? = false,
    val message: String = String(),
    val message_html: String = String(),
    val message_summary: String = String(),
    val permalink: String = String(),
    val position: Int = 0,
    val quoteUrl: String = String(),
    val reaction: ReactionUser? = ReactionUser(),
    val reactionCounts: List<ReactionCount> = listOf(),
    val reactionUsers: List<ReactionUser> = listOf(),
    val reaction_score: Int = 0,
    val reportUrl: String = String(),
    val summary: String = String(),
    val threadViewPage: Int = 0,
    val thread_id: Int = 0,
    val user: User? = User(),
    val userVote: Boolean = false,
    val user_id: Int = 0,
    val voteUrl: String = String(),
    val vote_count: Int = 0,
    val vote_score: Int = 0
)

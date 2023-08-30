package io.aayush.relabs.network

import io.aayush.relabs.network.data.alert.Alerts
import io.aayush.relabs.network.data.conversation.Conversations
import io.aayush.relabs.network.data.node.Nodes
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.network.data.thread.Threads
import io.aayush.relabs.network.data.user.Me
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface XenforoInterface {

    companion object {
        const val BASE_URL = "https://forum.xda-developers.com/api/"
    }

    @GET("me/")
    suspend fun getCurrentUser(): Response<Me>

    @GET("alerts/")
    suspend fun getAlerts(
        @Query("page") page: Int? = null,
        @Query("cutoff") cutoff: Int? = null,
        @Query("unviewed") unviewed: Boolean? = null,
        @Query("unread") unread: Boolean? = null
    ): Response<Alerts>

    @GET("conversations/")
    suspend fun getConversations(
        @Query("page") page: Int? = null,
        @Query("starter_id") starter_id: Int? = null,
        @Query("receiver_id") receiver_id: Int? = null,
        @Query("starred") starred: Boolean? = null,
        @Query("unread") unread: Boolean? = null
    ): Response<Conversations>

    @GET("nodes/")
    suspend fun getNodes(): Response<Nodes>

    @GET("threads/")
    suspend fun getThreads(
        @Query("page") page: Int? = null,
        @Query("prefix_id") prefix_id: Int? = null,
        @Query("starter_id") starter_id: Int? = null,
        @Query("last_days") last_days: Int? = null,
        @Query("unread") unread: Boolean? = null,
        @Query("thread_type") thread_type: String? = null,
        @Query("order") order: String? = null,
        @Query("direction") direction: String? = null
    ): Response<Threads>

    @GET("threads/audapp-watched/")
    suspend fun getWatchedThreads(): Response<Threads>

    @GET("threads/{id}")
    suspend fun getThreadInfo(
        @Path("id") id: Int,
        @Query("with_posts") with_posts: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("with_first_post") with_first_post: Boolean? = null,
        @Query("with_last_post") with_last_post: Boolean? = null,
        @Query("order") order: String? = null
    ): Response<ThreadInfo>

    @POST("posts")
    suspend fun postReply(
        @Query("thread_id") threadID: Int,
        @Query("message") message: String,
        @Query("attachment_key") attachmentKey: String? = null
    ): Response<PostReply>
}

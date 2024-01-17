package io.aayush.relabs.network

import io.aayush.relabs.network.data.alert.Alerts
import io.aayush.relabs.network.data.common.Success
import io.aayush.relabs.network.data.devices.Devices
import io.aayush.relabs.network.data.node.Nodes
import io.aayush.relabs.network.data.post.PostInfo
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.react.PostReact
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.network.data.thread.Threads
import io.aayush.relabs.network.data.user.Me
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface XDAInterface {

    companion object {
        const val BASE_URL = "https://xdaforums.com/api/"
    }

    @GET("me/")
    suspend fun getCurrentUser(): Response<Me>

    @GET("audapp-alerts/")
    suspend fun getAlerts(
        @Query("page") page: Int? = null,
        @Query("cutoff") cutoff: Int? = null,
        @Query("unviewed") unviewed: Boolean? = null,
        @Query("unread") unread: Boolean? = null
    ): Response<Alerts>

    @POST("alerts/mark-all/")
    suspend fun markAllAlerts(
        @Query("read") read: Boolean? = null,
        @Query("viewed") viewed: Boolean? = null
    ): Response<Success>

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

    @GET("forums/{id}/threads/")
    suspend fun getThreadsByNode(@Path("id") nodeID: Int): Response<Threads>

    @POST("threads/{id}/audapp-watch")
    suspend fun watchThread(@Path("id") threadID: Int): Response<Success>

    @POST("threads/{id}/audapp-unwatch")
    suspend fun unwatchThread(@Path("id") threadID: Int): Response<Success>

    @GET("threads/{id}")
    suspend fun getThreadInfo(
        @Path("id") id: Int,
        @Query("with_posts") with_posts: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("with_first_post") with_first_post: Boolean? = null,
        @Query("with_last_post") with_last_post: Boolean? = null,
        @Query("order") order: String? = null
    ): Response<ThreadInfo>

    @GET("posts/{id}")
    suspend fun getPostInfo(@Path("id") id: Int): Response<PostInfo>

    @POST("threads/{id}/mark-read")
    suspend fun markThreadAsRead(@Path("id") id: Int): Response<Success>

    @POST("posts")
    suspend fun postReply(
        @Query("thread_id") threadID: Int,
        @Query("message") message: String,
        @Query("attachment_key") attachmentKey: String? = null
    ): Response<PostReply>

    @POST("posts/{id}/react")
    suspend fun postReact(
        @Path("id") postID: Int,
        @Query("reaction_id") reactionID: Int
    ): Response<PostReact>

    @GET("xda-devices/inventory/")
    suspend fun getInventory(): Response<Devices>

    @GET("nodes/audapp-watched/")
    suspend fun getWatchedNodes(): Response<Nodes>

    @POST("audapp-push-subscriptions")
    suspend fun postExpoPushToken(@Body body: RequestBody): Response<Success>
}

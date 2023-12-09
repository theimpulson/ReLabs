package io.aayush.relabs.network

import android.util.Log
import io.aayush.relabs.network.data.alert.Alerts
import io.aayush.relabs.network.data.common.MarkResponse
import io.aayush.relabs.network.data.conversation.Conversations
import io.aayush.relabs.network.data.node.Node
import io.aayush.relabs.network.data.node.Nodes
import io.aayush.relabs.network.data.post.PostInfo
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.react.PostReact
import io.aayush.relabs.network.data.react.React
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.network.data.thread.ThreadWatchResponse
import io.aayush.relabs.network.data.thread.Threads
import io.aayush.relabs.network.data.user.Me
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XenforoRepository @Inject constructor(
    private val xenforoInterface: XenforoInterface
) {

    private val TAG = XenforoRepository::class.java.simpleName

    suspend fun getCurrentUser(): Me? {
        return safeExecute { xenforoInterface.getCurrentUser() }
    }

    suspend fun markAllAlerts(read: Boolean? = null, viewed: Boolean? = null): MarkResponse? {
        return safeExecute { xenforoInterface.markAllAlerts(read, viewed) }
    }

    suspend fun getAlerts(
        page: Int? = null,
        cutoff: Int? = null,
        unviewed: Boolean? = null,
        unread: Boolean? = null
    ): Alerts? {
        return safeExecute { xenforoInterface.getAlerts(page, cutoff, unviewed, unread) }
    }

    suspend fun getConversations(
        page: Int? = null,
        starter_id: Int? = null,
        receiver_id: Int? = null,
        starred: Boolean? = null,
        unread: Boolean? = null
    ): Conversations? {
        return safeExecute {
            xenforoInterface.getConversations(
                page,
                starter_id,
                receiver_id,
                starred,
                unread
            )
        }
    }

    suspend fun getNodes(): Nodes? {
        return safeExecute { xenforoInterface.getNodes() }
    }

    suspend fun getThreads(
        page: Int? = null,
        prefix_id: Int? = null,
        starter_id: Int? = null,
        last_days: Int? = null,
        unread: Boolean? = null,
        thread_type: String? = null,
        order: String? = null,
        direction: String? = null
    ): Threads? {
        return safeExecute {
            xenforoInterface.getThreads(
                page,
                prefix_id,
                starter_id,
                last_days,
                unread,
                thread_type,
                order,
                direction
            )
        }
    }

    suspend fun getWatchedThreads(): Threads? {
        return safeExecute { xenforoInterface.getWatchedThreads() }
    }

    suspend fun getThreadsByNode(nodeID: Int): Threads? {
        return safeExecute { xenforoInterface.getThreadsByNode(nodeID) }
    }

    suspend fun watchThread(threadID: Int): ThreadWatchResponse? {
        return safeExecute { xenforoInterface.watchThread(threadID) }
    }

    suspend fun unwatchThread(threadID: Int): ThreadWatchResponse? {
        return safeExecute { xenforoInterface.unwatchThread(threadID) }
    }

    suspend fun getThreadInfo(
        id: Int,
        with_posts: Boolean? = null,
        page: Int? = null,
        with_first_post: Boolean? = null,
        with_last_post: Boolean? = null,
        order: String? = null
    ): ThreadInfo? {
        return safeExecute {
            xenforoInterface.getThreadInfo(
                id,
                with_posts,
                page,
                with_first_post,
                with_last_post,
                order
            )
        }
    }

    suspend fun getPostInfo(id: Int): PostInfo? {
        return safeExecute { xenforoInterface.getPostInfo(id) }
    }

    suspend fun markThreadAsRead(threadID: Int): MarkResponse? {
        return safeExecute { xenforoInterface.markThreadAsRead(threadID) }
    }

    suspend fun postReply(
        threadID: Int,
        message: String,
        attachmentKey: String? = null
    ): PostReply? {
        return safeExecute { xenforoInterface.postReply(threadID, message, attachmentKey) }
    }

    suspend fun postReact(postID: Int, reactID: React): PostReact? {
        return safeExecute { xenforoInterface.postReact(postID, reactID.ordinal + 1) }
    }

    suspend fun getInventory(): List<Node>? {
        return safeExecute { xenforoInterface.getInventory() }?.devices?.map { it.Node }
    }

    suspend fun getWatchedNodes(): List<Node>? {
        return safeExecute { xenforoInterface.getWatchedNodes() }?.nodes
    }

    private inline fun <T> safeExecute(block: () -> Response<T>): T? {
        return try {
            val response = block()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()?.string()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to execute retrofit call!", exception)
            null
        }
    }
}

package io.aayush.relabs.network

import android.util.Log
import io.aayush.relabs.network.data.alert.Alerts
import io.aayush.relabs.network.data.alert.MarkAlert
import io.aayush.relabs.network.data.conversation.Conversations
import io.aayush.relabs.network.data.node.Nodes
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.react.PostReact
import io.aayush.relabs.network.data.react.React
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.network.data.thread.Threads
import io.aayush.relabs.network.data.user.Me
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XenforoRepository @Inject constructor(
    private val xenforoInterface: XenforoInterface
) {

    private val TAG = XenforoRepository::class.java.simpleName

    suspend fun getCurrentUser(): Me? {
        return try {
            val response = xenforoInterface.getCurrentUser()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch user details!", exception)
            null
        }
    }

    suspend fun markAllAlerts(read: Boolean, viewed: Boolean? = null): MarkAlert? {
        return try {
            val response = xenforoInterface.markAllAlerts(read, viewed)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to mark alerts!", exception)
            null
        }
    }

    suspend fun getAlerts(
        page: Int? = null,
        cutoff: Int? = null,
        unviewed: Boolean? = null,
        unread: Boolean? = null
    ): Alerts? {
        return try {
            val response = xenforoInterface.getAlerts(page, cutoff, unviewed, unread)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch alerts!", exception)
            null
        }
    }

    suspend fun getConversations(
        page: Int? = null,
        starter_id: Int? = null,
        receiver_id: Int? = null,
        starred: Boolean? = null,
        unread: Boolean? = null
    ): Conversations? {
        return try {
            val response = xenforoInterface.getConversations(
                page,
                starter_id,
                receiver_id,
                starred,
                unread
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch conversations!", exception)
            null
        }
    }

    suspend fun getNodes(): Nodes? {
        return try {
            val response = xenforoInterface.getNodes()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch nodes!", exception)
            null
        }
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
        return try {
            val response = xenforoInterface.getThreads(
                page,
                prefix_id,
                starter_id,
                last_days,
                unread,
                thread_type,
                order,
                direction
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch threads!", exception)
            null
        }
    }

    suspend fun getWatchedThreads(): Threads? {
        return try {
            val response = xenforoInterface.getWatchedThreads()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch watched threads!", exception)
            null
        }
    }

    suspend fun getThreadInfo(
        id: Int,
        with_posts: Boolean? = null,
        page: Int? = null,
        with_first_post: Boolean? = null,
        with_last_post: Boolean? = null,
        order: String? = null
    ): ThreadInfo? {
        return try {
            val response = xenforoInterface.getThreadInfo(
                id,
                with_posts,
                page,
                with_first_post,
                with_last_post,
                order
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch thread info!", exception)
            null
        }
    }

    suspend fun postReply(
        threadID: Int,
        message: String,
        attachmentKey: String? = null
    ): PostReply? {
        return try {
            val response = xenforoInterface.postReply(threadID, message, attachmentKey)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to post reply!", exception)
            null
        }
    }

    suspend fun postReact(postID: Int, reactID: React): PostReact? {
        return try {
            val response = xenforoInterface.postReact(postID, reactID.ordinal + 1)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.i(TAG, "Status: ${response.code()}, ${response.errorBody()}")
                null
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to react to the post!", exception)
            null
        }
    }
}

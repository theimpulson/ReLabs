package io.aayush.relabs.network

import android.util.Log
import androidx.paging.PagingData
import io.aayush.relabs.network.data.common.Success
import io.aayush.relabs.network.data.alert.Alerts
import io.aayush.relabs.network.data.expo.ExpoData
import io.aayush.relabs.network.data.node.Node
import io.aayush.relabs.network.data.post.PostInfo
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.react.PostReact
import io.aayush.relabs.network.data.react.React
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.network.data.thread.Threads
import io.aayush.relabs.network.data.user.Me
import io.aayush.relabs.network.paging.GenericPagingSource.Companion.createPager
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XDARepository @Inject constructor(
    private val expoInterface: ExpoInterface,
    private val xdaInterface: XDAInterface
) {

    private val TAG = XDARepository::class.java.simpleName

    suspend fun getCurrentUser(): Me? {
        return safeExecute { xdaInterface.getCurrentUser() }
    }

    suspend fun markAllAlerts(read: Boolean? = null, viewed: Boolean? = null): Success? {
        return safeExecute { xdaInterface.markAllAlerts(read, viewed) }
    }

    suspend fun getAlerts(
        page: Int? = null,
        cutoff: Int? = null,
        unviewed: Boolean? = null,
        unread: Boolean? = null
    ): Alerts? {
        return safeExecute { xdaInterface.getAlerts(page, cutoff, unviewed, unread) }
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
            xdaInterface.getThreads(
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
        return safeExecute { xdaInterface.getWatchedThreads() }
    }

    fun getThreadsByNode(nodeID: Int): Flow<PagingData<Thread>> {
        return createPager { page ->
            val threads = safeExecute { xdaInterface.getThreadsByNode(nodeID, page) }
            threads?.let { if (page == 1) it.sticky + it.threads else it.threads }.orEmpty()
        }.flow
    }

    suspend fun watchThread(threadID: Int): Success? {
        return safeExecute { xdaInterface.watchThread(threadID) }
    }

    suspend fun unwatchThread(threadID: Int): Success? {
        return safeExecute { xdaInterface.unwatchThread(threadID) }
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
            xdaInterface.getThreadInfo(
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
        return safeExecute { xdaInterface.getPostInfo(id) }
    }

    suspend fun markThreadAsRead(threadID: Int): Success? {
        return safeExecute { xdaInterface.markThreadAsRead(threadID) }
    }

    suspend fun registerPushNotifications(expoData: ExpoData): Boolean? {
        // Get token from Expo and register that on XDA
        val expoPushToken =
            safeExecute { expoInterface.getExpoPushToken(expoData) }?.data?.expoPushToken

        return if (!expoPushToken.isNullOrBlank()) {
            val body = MultipartBody.Builder(UUID.randomUUID().toString())
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", expoPushToken)
                .build()
            safeExecute { xdaInterface.postExpoPushToken(body) }?.success
        } else {
            false
        }
    }

    suspend fun postReply(
        threadID: Int,
        message: String,
        attachmentKey: String? = null
    ): PostReply? {
        return safeExecute { xdaInterface.postReply(threadID, message, attachmentKey) }
    }

    suspend fun postReact(postID: Int, reactID: React): PostReact? {
        return safeExecute { xdaInterface.postReact(postID, reactID.ordinal + 1) }
    }

    suspend fun getInventory(): List<Node>? {
        return safeExecute { xdaInterface.getInventory() }?.devices?.map { it.Node }
    }

    suspend fun getWatchedNodes(): List<Node>? {
        return safeExecute { xdaInterface.getWatchedNodes() }?.nodes
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

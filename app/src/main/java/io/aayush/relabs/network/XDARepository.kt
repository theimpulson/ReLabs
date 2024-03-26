package io.aayush.relabs.network

import android.util.Log
import androidx.paging.PagingData
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.network.data.common.Success
import io.aayush.relabs.network.data.expo.ExpoData
import io.aayush.relabs.network.data.node.Node
import io.aayush.relabs.network.data.post.PostInfo
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.react.PostReact
import io.aayush.relabs.network.data.react.React
import io.aayush.relabs.network.data.search.Type
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.network.data.user.Me
import io.aayush.relabs.network.paging.GenericPagingSource.Companion.createPager
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

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

    fun getAlerts(): Flow<PagingData<UserAlert>> {
        return createPager { page ->
            val response = safeExecute { xdaInterface.getAlerts(page) }
            mapOf(response?.pagination?.last_page to response?.alerts.orEmpty())
        }.flow
    }

    fun getThreads(): Flow<PagingData<Thread>> {
        return createPager { page ->
            val response = safeExecute { xdaInterface.getThreads(page) }
            mapOf(response?.pagination?.last_page to response?.threads.orEmpty())
        }.flow
    }

    fun getWatchedThreads(): Flow<PagingData<Thread>> {
        return createPager { page ->
            val response = safeExecute { xdaInterface.getWatchedThreads(page) }
            mapOf(response?.pagination?.last_page to response?.threads.orEmpty())
        }.flow
    }

    fun getThreadsByNode(nodeID: Int): Flow<PagingData<Thread>> {
        return createPager { page ->
            val response = safeExecute { xdaInterface.getThreadsByNode(nodeID, page) }
            mapOf(
                Pair(
                    response?.pagination?.last_page,
                    response?.let { if (page == 1) it.sticky + it.threads else it.threads }
                        .orEmpty()
                )
            )
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

    fun getSearchResultsForThreads(query: String): Flow<PagingData<Thread>> {
        return createPager { page ->
            val search = safeExecute { xdaInterface.postSearch(query, Type.THREAD.value) }?.search

            if (search != null && search.id != 0) {
                val res = safeExecute { xdaInterface.getSearchResultsForThread(search.id, page) }
                mapOf(res?.pagination?.last_page to res?.results.orEmpty())
            } else {
                emptyMap()
            }
        }.flow
    }

    fun getSearchResultsForNodes(query: String): Flow<PagingData<Node>> {
        return createPager { page ->
            val search = safeExecute { xdaInterface.postSearch(query, Type.NODE.value) }?.search

            if (search != null && search.id != 0) {
                val res = safeExecute { xdaInterface.getSearchResultsForNode(search.id, page) }
                mapOf(res?.pagination?.last_page to res?.results.orEmpty())
            } else {
                emptyMap()
            }
        }.flow
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

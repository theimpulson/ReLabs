package io.aayush.relabs.network

import io.aayush.relabs.network.data.alert.Alerts
import io.aayush.relabs.network.data.conversation.Conversations
import io.aayush.relabs.network.data.node.Nodes
import io.aayush.relabs.network.data.thread.Threads
import io.aayush.relabs.network.data.user.Me
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XenforoRepository @Inject constructor(
    private val xenforoInterface: XenforoInterface
) {

    suspend fun getCurrentUser(): Me {
        return xenforoInterface.getCurrentUser()
    }

    suspend fun getAlerts(
        page: Int? = null,
        cutoff: Int? = null,
        unviewed: Boolean? = null,
        unread: Boolean? = null
    ): Alerts {
        return xenforoInterface.getAlerts(page, cutoff, unviewed, unread)
    }

    suspend fun getConversations(
        page: Int? = null,
        starter_id: Int? = null,
        receiver_id: Int? = null,
        starred: Boolean? = null,
        unread: Boolean? = null
    ): Conversations {
        return xenforoInterface.getConversations(
            page,
            starter_id,
            receiver_id,
            starred,
            unread
        )
    }

    suspend fun getNodes(): Nodes {
        return xenforoInterface.getNodes()
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
    ): Threads {
        return xenforoInterface.getThreads(
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

    suspend fun getWatchedThreads(): Threads {
        return xenforoInterface.getWatchedThreads()
    }
}

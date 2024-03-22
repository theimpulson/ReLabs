package io.aayush.relabs.ui.screens.node

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.components.ThreadPreviewItem
import io.aayush.relabs.ui.navigation.Screen

@Composable
fun NodeScreen(
    navHostController: NavHostController,
    nodeID: Int,
    nodeTitle: String = String(),
    viewModel: NodeViewModel = hiltViewModel()
) {
    val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()
    val threads: List<Thread>? by viewModel.threads.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getThreads(nodeID)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(
                screen = Screen.Node,
                navHostController = navHostController,
                title = nodeTitle
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (loading) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(20) {
                        ThreadPreviewItem(modifier = Modifier.padding(10.dp), loading = true)
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(items = threads ?: emptyList(), key = { t -> t.id }) { thread ->
                        ThreadPreviewItem(
                            modifier = Modifier.padding(10.dp),
                            avatarURL = thread.user.avatar?.data?.medium ?: String(),
                            title = thread.title,
                            author = thread.user.username,
                            totalReplies = thread.reply_count,
                            views = thread.view_count,
                            lastReplyDate = thread.last_post_at.long,
                            lastReplyAuthor = thread.last_post_username,
                            unread = thread.isUnread,
                            onClicked = {
                                navHostController.navigate(Screen.Thread.withID(thread.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

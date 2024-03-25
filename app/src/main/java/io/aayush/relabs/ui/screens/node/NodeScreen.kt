package io.aayush.relabs.ui.screens.node

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
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

    val threads = viewModel.threads.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        if (threads.itemSnapshotList.isEmpty()) viewModel.getThreads(nodeID)
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
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it)
        ) {
            when (threads.loadState.refresh) {
                is LoadState.Error -> {
                    // TODO: Handle first load error
                }
                is LoadState.Loading -> {
                    items(20) {
                        ThreadPreviewItem(modifier = Modifier.padding(10.dp), loading = true)
                    }
                }
                else -> {
                    items(
                        count = threads.itemCount,
                        key = threads.itemKey { t -> t.id },
                        contentType = threads.itemContentType { "Threads" }
                    ) { index ->
                        val thread = threads[index] ?: return@items
                        ThreadPreviewItem(
                            modifier = Modifier.padding(10.dp),
                            avatarURL = thread.user?.avatar?.data?.medium ?: String(),
                            title = thread.title,
                            author = thread.user?.username,
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

package io.aayush.relabs.ui.screens.node

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.ui.components.ThreadPreviewItem
import io.aayush.relabs.ui.navigation.Screen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = {
                    Text(
                        text = nodeTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(items = threads ?: emptyList(), key = { t -> t.thread_id }) { thread ->
                    ThreadPreviewItem(
                        modifier = Modifier.padding(10.dp),
                        avatarURL = thread.User?.avatar_urls?.values?.first() ?: "",
                        title = thread.title,
                        author = thread.username,
                        totalReplies = thread.reply_count,
                        views = thread.view_count,
                        lastReplyDate = thread.last_post_date,
                        lastReplyAuthor = thread.last_post_username,
                        forum = thread.Forum.title,
                        unread = thread.is_unread,
                        onClicked = {
                            navHostController.navigate(Screen.Thread.withID(thread.thread_id))
                        }
                    )
                }
            }
        }
    }
}

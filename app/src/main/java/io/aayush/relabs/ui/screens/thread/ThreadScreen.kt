package io.aayush.relabs.ui.screens.thread

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.ui.components.ErrorScreen
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.components.PostItem
import io.aayush.relabs.ui.navigation.Screen
import io.aayush.relabs.utils.Error
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ThreadScreen(
    navHostController: NavHostController,
    threadID: Int,
    viewModel: ThreadViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getThreadInfo(threadID)
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val threadInfo: ThreadInfo? by viewModel.threadInfo.collectAsStateWithLifecycle()
    val postsToQuote = remember { viewModel.postsToQuote }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(
                screen = Screen.Thread,
                navHostController = navHostController,
                title = threadInfo?.thread?.title ?: String()
            ) {
                if (threadInfo?.thread?.thread_id != 0) {
                    IconButton(onClick = {
                        if (threadInfo?.thread?.is_watching == true) {
                            viewModel.unwatchThread(threadInfo?.thread?.thread_id ?: 0)
                        } else {
                            viewModel.watchThread(threadInfo?.thread?.thread_id ?: 0)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (threadInfo?.thread?.is_watching == true) {
                                    R.drawable.ic_watch_filled
                                } else {
                                    R.drawable.ic_watch
                                }
                            ),
                            contentDescription = ""
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (threadInfo?.thread?.discussion_open == true) {
                ExtendedFloatingActionButton(
                    text = { Text(text = stringResource(id = Screen.Reply.title)) },
                    icon = {
                        Image(
                            painter = painterResource(id = Screen.Reply.icon),
                            contentDescription = ""
                        )
                    },
                    onClick = {
                        navHostController.navigate(Screen.Reply.withID(threadID))
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { threadInfo?.pagination?.last_page ?: 0 }
            )
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            LaunchedEffect(key1 = pagerState) {
                snapshotFlow { pagerState.targetPage }.collect { page ->
                    // XenForo considers current page as 1
                    viewModel.getPosts(page + 1)

                    // Mark thread as read if this is last page
                    if (pagerState.settledPage == pagerState.pageCount) {
                        viewModel.markThreadAsRead(threadID)
                    }
                }
            }

            if (threadInfo == null) {
                ErrorScreen(error = Error.EMPTY, retry = { viewModel.getThreadInfo(threadID) })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(0) }
                    },
                    enabled = pagerState.settledPage != 0
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_previous), contentDescription = "")
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.settledPage - 1) }
                    },
                    enabled = pagerState.settledPage != 0
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "")
                }

                Text(text = "${pagerState.settledPage + 1}/${pagerState.pageCount}")

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.settledPage + 1)
                        }
                    },
                    enabled = pagerState.settledPage + 1 != threadInfo?.pagination?.last_page
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "")
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(threadInfo?.pagination?.last_page ?: 0)
                        }
                    },
                    enabled = pagerState.settledPage + 1 != threadInfo?.pagination?.last_page
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_next), contentDescription = "")
                }
            }

            Divider(thickness = 1.dp)

            HorizontalPager(state = pagerState) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = viewModel.posts.getOrElse(it) { emptyList() }
                            ?.filter { p -> p.message_state != "deleted" } ?: emptyList(),
                        key = { p -> p.post_id }
                    ) { post ->
                        PostItem(
                            post = post,
                            linkTransformationMethod = viewModel.linkTransformationMethod,
                            designQuoteSpan = viewModel.designQuoteSpan,
                            isThreadOwner = post.User?.username == threadInfo?.thread?.User?.username,
                            isThreadOpen = threadInfo?.thread?.discussion_open ?: true,
                            reactionScore = post.reaction_score,
                            reacted = post.is_reacted_to,
                            quoted = post in postsToQuote,
                            onReact = { viewModel.reactToPost(pagerState.settledPage, post) },
                            onQuote = {
                                viewModel.postsToQuote.add(post)
                                navHostController.navigate(Screen.Reply.withID(threadID))
                            },
                            onMultiQuote = {
                                val quoted = if (post !in postsToQuote) {
                                    viewModel.postsToQuote.add(post)
                                    true
                                } else {
                                    viewModel.postsToQuote.remove(post)
                                    false
                                }
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = if (quoted) {
                                            context.getString(R.string.added_quote_list)
                                        } else {
                                            context.getString(R.string.removed_quote_list)
                                        }
                                    )
                                }
                            },
                            onShare = {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        context.getString(R.string.share_post_header, post.view_url)
                                    )
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(intent, null))
                            }
                        )
                    }
                }
            }
        }
    }
}

/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.screens.threadpreview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.aayush.relabs.R
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.components.ThreadPreviewItem
import io.aayush.relabs.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ThreadPreviewScreen(
    navHostController: NavHostController,
    viewModel: ThreadPreviewViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(screen = Screen.ThreadPreview, navHostController = navHostController)
        }
    ) {
        val tabData = listOf(R.string.watched, R.string.whats_new)
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { tabData.size }
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        val watchedThreads = viewModel.watchedThreads.collectAsLazyPagingItems()
        val trendingThreads = viewModel.trendingThreads.collectAsLazyPagingItems()

        Column(modifier = Modifier.padding(it)) {
            TabRow(selectedTabIndex = tabIndex) {
                tabData.fastForEachIndexed { index, _ ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(text = stringResource(id = tabData[index]))
                        }
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    val currentThreads = when (it) {
                        0 -> watchedThreads
                        else -> trendingThreads
                    }
                    when (currentThreads.loadState.refresh) {
                        is LoadState.Error -> {}
                        is LoadState.Loading -> {
                            items(20) {
                                ThreadPreviewItem(
                                    modifier = Modifier.padding(10.dp),
                                    loading = true
                                )
                            }
                        }

                        else -> {
                            items(
                                count = currentThreads.itemCount,
                                key = currentThreads.itemKey { t -> t.id },
                                contentType = currentThreads.itemContentType { Thread::class.java },
                            ) { index ->
                                val thread = currentThreads[index] ?: return@items
                                ThreadPreviewItem(
                                    modifier = Modifier.padding(10.dp),
                                    avatarURL = thread.user.avatar?.data?.medium ?: String(),
                                    title = thread.title,
                                    author = thread.user.username,
                                    totalReplies = thread.reply_count,
                                    views = thread.view_count,
                                    lastReplyDate = thread.last_post_at.long,
                                    forum = thread.node.title,
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
    }
}

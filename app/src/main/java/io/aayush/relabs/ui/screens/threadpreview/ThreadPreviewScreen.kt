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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.thread.DiscussionState
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

        val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()
        val watchedThreads: List<Thread>? by viewModel.watchedThreads.collectAsStateWithLifecycle()
        val trendingThreads: List<Thread>? by viewModel.trendingThreads.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                when (page) {
                    0 -> viewModel.getWatchedThreads()
                    1 -> viewModel.getTrendingThreads()
                }
            }
        }

        Column(modifier = Modifier.padding(it)) {
            TabRow(selectedTabIndex = tabIndex) {
                tabData.fastForEachIndexed { index, _ ->
                    Tab(selected = tabIndex == index, onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }, text = {
                            Text(text = stringResource(id = tabData[index]))
                        })
                }
            }
            HorizontalPager(
                state = pagerState
            ) {
                if (loading) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(20) {
                            ThreadPreviewItem(modifier = Modifier.padding(10.dp), loading = true)
                        }
                    }
                    return@HorizontalPager
                }

                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(
                        items = when (it) {
                            0 ->
                                watchedThreads
                                    ?.filter { t -> t.discussion_state == DiscussionState.VISIBLE }
                                    ?: emptyList()

                            else -> trendingThreads ?: emptyList()
                        },
                        key = { t -> t.thread_id }
                    ) { thread ->
                        ThreadPreviewItem(
                            modifier = Modifier.padding(10.dp),
                            avatarURL = thread.User?.avatar_urls?.values?.first() ?: "",
                            title = thread.title,
                            author = thread.username,
                            totalReplies = thread.reply_count,
                            views = thread.view_count,
                            lastReplyDate = thread.last_post_date,
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
}

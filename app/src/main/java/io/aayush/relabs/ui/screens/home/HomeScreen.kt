/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.ui.components.ThreadPreviewItem
import io.aayush.relabs.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.home)) }) }
    ) {
        val tabData = listOf(R.string.watched, R.string.trending)
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { tabData.size }
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        val watchedThreads: List<Thread> by viewModel.watchedThreads.collectAsStateWithLifecycle()
        val trendingThreads: List<Thread> by viewModel.trendingThreads.collectAsStateWithLifecycle()

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
                tabData.forEachIndexed { index, _ ->
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
            HorizontalPager(
                state = pagerState
            ) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(
                        when (it) {
                            0 -> watchedThreads
                            else -> trendingThreads
                        }
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

/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.screens.threadpreview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.aayush.relabs.R
import io.aayush.relabs.network.data.thread.State
import io.aayush.relabs.network.data.thread.Thread
import io.aayush.relabs.ui.components.ThreadPreviewItem
import io.aayush.relabs.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun ThreadPreviewScreen(
    navHostController: NavHostController,
    viewModel: ThreadPreviewViewModel = hiltViewModel()
) {

    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val shouldShowSearchResults by viewModel.shouldShowSearchResults.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val searchText by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    modifier = Modifier.align(Alignment.Center),
                    query = searchText,
                    onQueryChange = viewModel::updateQuery,
                    onSearch = { viewModel.search(searchText) },
                    active = isSearching,
                    onActiveChange = {
                        viewModel.isSearching.value = it
                        if (!it) viewModel.updateQuery("")
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.search_hint_threads))
                    },
                    leadingIcon = {
                        if (isSearching) {
                            IconButton(
                                onClick = {
                                    viewModel.isSearching.value = false
                                    viewModel.updateQuery("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        } else {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "")
                        }
                    },
                    trailingIcon = {
                        if (isSearching) {
                            IconButton(
                                onClick = { viewModel.updateQuery("") },
                                enabled = searchText.isNotBlank()
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "")
                            }
                        }
                    }
                ) {
                    if (shouldShowSearchResults) {
                        ThreadItems(currentThreads = searchResults, navHostController = navHostController)
                    }
                }
            }
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
                val currentThreads = when (it) {
                    0 -> watchedThreads
                    else -> trendingThreads
                }
                ThreadItems(currentThreads = currentThreads, navHostController = navHostController)
            }
        }
    }
}

@Composable
private fun ThreadItems(currentThreads: LazyPagingItems<Thread>, navHostController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
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
                    if (thread.state != State.VISIBLE) return@items

                    ThreadPreviewItem(
                        modifier = Modifier.padding(10.dp),
                        avatarURL = thread.user?.avatar?.data?.medium ?: String(),
                        title = thread.title,
                        author = thread.user?.username,
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

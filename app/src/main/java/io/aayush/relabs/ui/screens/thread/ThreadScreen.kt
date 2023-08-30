package io.aayush.relabs.ui.screens.thread

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.ui.components.PostItem
import io.aayush.relabs.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun ThreadScreen(
    navHostController: NavHostController,
    threadID: Int,
    viewModel: ThreadViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getThreadInfo(threadID)
    }

    val threadInfo: ThreadInfo? by viewModel.threadInfo.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = threadInfo?.thread?.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
        },
        floatingActionButton = {
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
    ) {
        Column(modifier = Modifier.padding(it)) {
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { threadInfo?.pagination?.last_page ?: 0 }
            )
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = pagerState) {
                snapshotFlow { pagerState.targetPage }.collect { page ->
                    // XenForo considers current page as 1
                    if (page != 0) viewModel.getPosts(page + 1)
                }
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
                    Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "")
                }

                Text(text = "${pagerState.settledPage + 1}/${pagerState.pageCount}")

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(threadInfo?.pagination?.last_page ?: 0)
                        }
                    },
                    enabled = pagerState.settledPage + 1 != threadInfo?.pagination?.last_page
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "")
                }
            }

            Divider(thickness = 1.dp)

            HorizontalPager(state = pagerState) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = viewModel.posts.getOrElse(it) { emptyList() } ?: emptyList(),
                        key = { p -> p.post_id }
                    ) { post ->
                        PostItem(
                            post = post,
                            linkTransformationMethod = viewModel.linkTransformationMethod,
                            designQuoteSpan = viewModel.designQuoteSpan,
                            isThreadOwner = post.User?.username == threadInfo?.thread?.User?.username,
                            reactionScore = post.reaction_score,
                            reacted = post.is_reacted_to
                        )
                    }
                }
            }
        }
    }
}

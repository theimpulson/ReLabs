package io.aayush.relabs.ui.screens.nodepreview

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
import io.aayush.relabs.network.data.node.Kind
import io.aayush.relabs.network.data.node.Node
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.components.NodePreviewItem
import io.aayush.relabs.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NodePreviewScreen(
    navHostController: NavHostController,
    viewModel: NodePreviewViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(screen = Screen.NodePreview, navHostController = navHostController)
        }
    ) {
        val tabData = listOf(R.string.inventory, R.string.watched)
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { tabData.size }
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()
        val inventory: List<Node>? by viewModel.inventory.collectAsStateWithLifecycle()
        val watchedNodes: List<Node>? by viewModel.watchedNodes.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                when (page) {
                    0 -> viewModel.getInventory()
                    1 -> viewModel.getWatchedNodes()
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
                            NodePreviewItem(modifier = Modifier.padding(10.dp), loading = true)
                        }
                    }
                    return@HorizontalPager
                }

                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(
                        items = when (it) {
                            0 -> inventory ?: emptyList()
                            1 -> watchedNodes ?: emptyList()
                            else -> emptyList()
                        },
                        key = { n -> n.id }
                    ) { node ->
                        // TODO: Figure out API for browsing vBulletin categories
                        if (node.kind != Kind.FORUM) return@items

                        NodePreviewItem(
                            modifier = Modifier.padding(10.dp),
                            title = node.title,
                            company = node.breadcrumb_data.values.firstOrNull()?.title ?: String(),
                            lastUpdated = node.node_type_data.firstOrNull()?.last_post_date?.long ?: 0,
                            lastThreadTitle = node.node_type_data.firstOrNull()?.last_thread_title ?: "",
                            unread = node.node_type_data.firstOrNull()?.isUnread ?: false,
                            threads = node.node_type_data.firstOrNull()?.discussion_count ?: 0,
                            iconURL = node.xda.iconUrl ?: "",
                            onClicked = {
                                navHostController.navigate(
                                    Screen.Node.withIDAndTitle(
                                        node.id,
                                        node.title
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

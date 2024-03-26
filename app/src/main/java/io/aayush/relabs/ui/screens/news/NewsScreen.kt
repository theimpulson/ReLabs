package io.aayush.relabs.ui.screens.news

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.prof18.rssparser.model.RssItem
import io.aayush.relabs.R
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.components.NewsItem
import io.aayush.relabs.ui.navigation.Screen
import java.util.UUID
import kotlinx.coroutines.launch

private const val TAG = "NewsScreen"

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NewsScreen(navHostController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopAppBar(screen = Screen.News, navHostController = navHostController) }
    ) {
        val tabData = listOf(R.string.google_9to5, R.string.arstech, R.string.android_devs, R.string.xda_portal)
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { tabData.size }
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()
        val xdaPortalFeed: List<RssItem> by viewModel.xdaPortalFeed.collectAsStateWithLifecycle()
        val arsTechFeed: List<RssItem> by viewModel.arsTechFeed.collectAsStateWithLifecycle()
        val google9to5Feed: List<RssItem> by viewModel.google9to5Feed.collectAsStateWithLifecycle()
        val androidDevsFeed: List<RssItem> by viewModel.androidDevsFeed.collectAsStateWithLifecycle()

        val context = LocalContext.current

        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                when (page) {
                    0 -> viewModel.get9to5GoogleArticles()
                    1 -> viewModel.getArsTechArticles()
                    2 -> viewModel.getAndroidDevelopersArticles()
                    else -> viewModel.getXDAPortalArticles()
                }
            }
        }

        Column(modifier = Modifier.padding(it)) {
            ScrollableTabRow(selectedTabIndex = tabIndex, edgePadding = 0.dp) {
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
            HorizontalPager(
                state = pagerState
            ) {
                if (loading) {
                    LazyColumn {
                        items(20) {
                            NewsItem(modifier = Modifier.padding(10.dp), loading = true)
                        }
                    }
                    return@HorizontalPager
                }

                LazyColumn(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                ) {
                    items(
                        items = when (it) {
                            0 -> google9to5Feed
                            1 -> arsTechFeed
                            2 -> androidDevsFeed
                            else -> xdaPortalFeed
                        },
                        key = { a -> a.guid ?: UUID.randomUUID() }
                    ) { article ->
                        NewsItem(
                            thumbnail = article.image ?: "",
                            headline = article.title ?: "",
                            description = HtmlCompat.fromHtml(
                                article.description?.replace(Regex("(<(/)img>)|(<img.+?>)"), "")
                                    ?: "",
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            ).toString(),
                            author = stringResource(id = R.string.posted_by, article.author ?: ""),
                            date = article.pubDate ?: "",
                            onClicked = {
                                try {
                                    viewModel.customTabsIntent
                                        .launchUrl(context, Uri.parse(article.link))
                                } catch (exception: Exception) {
                                    Log.e(TAG, "Failed to open profile", exception)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

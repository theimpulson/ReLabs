package io.aayush.relabs.ui.screens.news

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.prof18.rssparser.model.RssItem
import io.aayush.relabs.R
import io.aayush.relabs.newsitem.NewsItem
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.extensions.shimmer
import io.aayush.relabs.ui.navigation.Screen
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "NewsScreen"

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NewsScreen(navHostController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopAppBar(screen = Screen.News, navHostController = navHostController) }
    ) {
        val tabData = listOf(R.string.android_devs, R.string.google_9to5, R.string.arstech, R.string.xda_portal)
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
                    0 -> viewModel.getAndroidDevelopersArticles()
                    1 -> viewModel.get9to5GoogleArticles()
                    2 -> viewModel.getArsTechArticles()
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
                            // TODO: Drop figma & relay
                            NewsItem(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 10.dp)
                                    .shimmer(true)
                            )
                        }
                    }
                    return@HorizontalPager
                }

                LazyColumn {
                    items(
                        items = when (it) {
                            0 -> androidDevsFeed
                            1 -> google9to5Feed
                            2 -> arsTechFeed
                            else -> xdaPortalFeed
                        },
                        key = { a -> a.guid ?: UUID.randomUUID() }
                    ) { article ->
                        NewsItem(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                            backgroundColor = if (isSystemInDarkTheme()) {
                                MaterialTheme.colorScheme.onSecondary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            },
                            thumbnail = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(article.image)
                                    .scale(Scale.FIT)
                                    .build()
                            ),
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

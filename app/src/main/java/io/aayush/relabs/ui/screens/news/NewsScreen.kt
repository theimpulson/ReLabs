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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.prof.rssparser.Article
import io.aayush.relabs.R
import io.aayush.relabs.newsitem.NewsItem
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "NewsScreen"

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun NewsScreen(navHostController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.news)) }) }
    ) {
        val tabData = listOf(R.string.mobile, R.string.computing, R.string.smart_home)
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { tabData.size }
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        val mobileFeed: List<Article> by viewModel.mobileFeed.collectAsStateWithLifecycle()
        val computingFeed: List<Article> by viewModel.computingFeed.collectAsStateWithLifecycle()
        val smartHomeFeed: List<Article> by viewModel.smartHomeFeed.collectAsStateWithLifecycle()

        val context = LocalContext.current

        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                when (page) {
                    0 -> viewModel.getMobileArticles()
                    1 -> viewModel.getComputingArticles()
                    2 -> viewModel.getSmartHomeArticles()
                }
            }
        }

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
            HorizontalPager(
                state = pagerState
            ) {
                LazyColumn {
                    items(
                        items = when (it) {
                            0 -> mobileFeed
                            1 -> computingFeed
                            else -> smartHomeFeed
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
                                article.content ?: "",
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

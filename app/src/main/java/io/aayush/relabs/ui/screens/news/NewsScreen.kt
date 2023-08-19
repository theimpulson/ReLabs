package io.aayush.relabs.ui.screens.news

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

private const val TAG = "NewsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    navHostController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.news)) },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        val articles: List<Article> by viewModel.feed.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LazyColumn(modifier = Modifier.padding(it)) {
            items(articles) { article ->
                NewsItem(
                    modifier = Modifier.padding(vertical = 10.dp),
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
                            CustomTabsIntent.Builder()
                                .build()
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

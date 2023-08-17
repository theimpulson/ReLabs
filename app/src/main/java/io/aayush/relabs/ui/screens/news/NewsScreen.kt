package io.aayush.relabs.ui.screens.news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.prof.rssparser.Article
import io.aayush.relabs.R
import io.aayush.relabs.ui.components.RSSFeedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    navHostController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.news)) }) }
    ) {
        val articles: List<Article> by viewModel.feed.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LazyColumn(modifier = Modifier.padding(it)) {
            items(articles) { article ->
                RSSFeedItem(context = context, article = article)
            }
        }
    }
}

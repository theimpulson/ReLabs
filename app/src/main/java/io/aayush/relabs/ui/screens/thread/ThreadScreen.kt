package io.aayush.relabs.ui.screens.thread

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.post.Post
import io.aayush.relabs.ui.components.PostItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ThreadScreen(
    navHostController: NavHostController,
    threadID: Int,
    viewModel: ThreadViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getThreadInfo(threadID)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.thread)) }) }
    ) {
        val postList: List<Post> by viewModel.posts.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(it)) {
            items(postList) { post ->
                PostItem(
                    modifier = Modifier.padding(10.dp),
                    post = post,
                    linkTransformationMethod = viewModel.linkTransformationMethod,
                    designQuoteSpan = viewModel.designQuoteSpan
                )
            }
        }
    }
}

package io.aayush.relabs.ui.screens.forumpreview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.aayush.relabs.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ForumPreviewScreen(
    navHostController: NavHostController,
    viewModel: ForumPreviewViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.forum_preview)) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
        }
    }
}

package io.aayush.relabs.ui.screens.alerts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.network.data.post.PostInfo
import io.aayush.relabs.ui.components.AlertItem
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.navigation.Screen

@Composable
fun AlertsScreen(
    navHostController: NavHostController,
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()
    val alerts: List<UserAlert>? by viewModel.alerts.collectAsStateWithLifecycle()
    val postInfo: PostInfo by viewModel.postInfo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = postInfo) {
        if (postInfo.post.thread_id != 0) {
            navHostController.navigate(Screen.Thread.withID(postInfo.post.thread_id))
            viewModel.postInfo.value = PostInfo()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(screen = Screen.Alerts, navHostController = navHostController) {
                IconButton(
                    onClick = { viewModel.markAllAlerts(read = true) },
                    enabled = alerts?.fastAny { it.read_date == 0 } == true
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done_all),
                        contentDescription = ""
                    )
                }
            }
        }
    ) {
        if (loading) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(20) {
                    AlertItem(modifier = Modifier.padding(10.dp), loading = true)
                }
            }
            return@Scaffold
        }

        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = alerts ?: emptyList(), key = { a -> a.alert_id }) { userAlert ->
                AlertItem(
                    modifier = Modifier.padding(10.dp),
                    avatarURL = userAlert.User?.avatar_urls?.values?.first() ?: "",
                    title = userAlert.alert_text,
                    date = userAlert.event_date,
                    unread = userAlert.read_date == 0,
                    onClicked = {
                        viewModel.getPostInfo(userAlert.content_id)
                    }
                )
            }
        }
    }
}

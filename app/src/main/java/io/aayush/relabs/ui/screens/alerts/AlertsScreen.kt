package io.aayush.relabs.ui.screens.alerts

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.alert.ContentType
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.network.data.post.PostInfo
import io.aayush.relabs.ui.components.AlertItem
import io.aayush.relabs.ui.components.MainTopAppBar
import io.aayush.relabs.ui.navigation.Screen

private const val TAG = "AlertsScreen"

@Composable
fun AlertsScreen(
    navHostController: NavHostController,
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()
    val alerts: List<UserAlert>? by viewModel.alerts.collectAsStateWithLifecycle()
    val postInfo: PostInfo by viewModel.postInfo.collectAsStateWithLifecycle()

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { Log.i(TAG, "Notification permission: $it") }
    )

    LaunchedEffect(key1 = Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

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
                IconButton(onClick = { openNotificationSettings(context) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications_edit),
                        contentDescription = ""
                    )
                }
                IconButton(
                    onClick = { viewModel.markAllAlerts(read = true) },
                    enabled = alerts?.fastAny { it.read_at == null } == true
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
            items(items = alerts ?: emptyList(), key = { a -> a.id }) { userAlert ->
                AlertItem(
                    modifier = Modifier.padding(10.dp),
                    avatarURL = userAlert.user?.avatar?.data?.medium ?: String(),
                    title = userAlert.message,
                    date = userAlert.created_at.long,
                    unread = userAlert.read_at == null,
                    onClicked = {
                        if (userAlert.content_type == ContentType.POST) {
                            viewModel.getPostInfo(userAlert.content_id)
                        }
                    }
                )
            }
        }
    }
}

@SuppressLint("InlinedApi")
private fun openNotificationSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra("app_package", context.packageName)
        putExtra("app_uid", context.applicationInfo.uid)
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}

package io.aayush.relabs.ui.screens.alerts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.ui.components.AlertItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AlertsScreen(
    navHostController: NavHostController,
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val alerts: List<UserAlert>? by viewModel.alerts.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.alerts)) },
                actions = {
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
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = alerts ?: emptyList(), key = { a -> a.alert_id }) { userAlert ->
                AlertItem(
                    modifier = Modifier.padding(10.dp),
                    avatarURL = userAlert.User?.avatar_urls?.values?.first() ?: "",
                    title = userAlert.alert_text,
                    date = userAlert.event_date,
                    unread = userAlert.read_date == 0
                )
            }
        }
    }
}

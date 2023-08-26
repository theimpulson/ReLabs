package io.aayush.relabs.ui.screens.settings

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.user.Me
import io.aayush.relabs.ui.components.UserHeader

private const val TAG = "SettingsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val user: Me by viewModel.currentUser.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                actions = {
                    if (user.me?.view_url?.isNotBlank() == true) {
                        val context = LocalContext.current
                        IconButton(onClick = {
                            try {
                                CustomTabsIntent.Builder()
                                    .build()
                                    .launchUrl(context, Uri.parse(viewModel.currentUser.value.me?.view_url))
                            } catch (exception: Exception) {
                                Log.e(TAG, "Failed to open profile", exception)
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_open),
                                contentDescription = ""
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user.me?.username?.isNotBlank() == true) {
                UserHeader(user = user.me!!)
            }
        }
    }
}

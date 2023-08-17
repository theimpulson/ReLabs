package io.aayush.relabs.ui.screens.settings

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import io.aayush.relabs.R
import io.aayush.relabs.network.data.user.Me
import io.aayush.relabs.ui.theme.XDAYellow

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
                    if (user.me.view_url.isNotBlank()) {
                        val context = LocalContext.current
                        IconButton(onClick = {
                            try {
                                CustomTabsIntent.Builder()
                                    .build()
                                    .launchUrl(context, Uri.parse(viewModel.currentUser.value.me.view_url))
                            } catch (exception: Exception) {
                                Log.e(TAG, "Failed to open profile", exception)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.OpenInNew,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
            )
        }
    ) {
        user.me.apply {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = if (username.isBlank()) Arrangement.Center else Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (username.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(avatar_urls.values.first())
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                loading = {
                                    CircularProgressIndicator()
                                },
                                modifier = Modifier
                                    .requiredSize(96.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = username, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(5.dp))
                                if (is_staff) {
                                    Image(
                                        imageVector = Icons.Filled.LocalPolice,
                                        contentDescription = "",
                                        colorFilter = ColorFilter.tint(XDAYellow)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = user_title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LinearProgressIndicator()
                }
            }
        }
    }
}

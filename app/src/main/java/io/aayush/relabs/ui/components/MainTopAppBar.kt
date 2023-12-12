package io.aayush.relabs.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import io.aayush.relabs.MainActivity
import io.aayush.relabs.ui.navigation.Screen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainTopAppBar(
    screen: Screen,
    navHostController: NavHostController,
    title: String = String(),
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            if (title.isBlank()) {
                Text(text = stringResource(id = screen.title))
            } else {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        navigationIcon = {
            if (screen !in MainActivity.topRoutes) {
                IconButton(onClick = { navHostController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
            actions()
            if (screen in MainActivity.topRoutes) {
                IconButton(onClick = { navHostController.navigate(Screen.More.route) }) {
                    Icon(
                        painter = painterResource(id = Screen.More.icon),
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

package io.aayush.relabs.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.aayush.relabs.R
import io.aayush.relabs.utils.Error

@Composable
fun ErrorScreen(
    error: Error,
    retry: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val message = when (error) {
            Error.RETROFIT -> {
                stringResource(
                    id = R.string.error_retrofit,
                    "retrofit2.HttpException:"
                )
            }
            Error.EMPTY -> {
                stringResource(
                    id = R.string.error_empty,
                    "java.util.NoSuchElementException:"
                )
            }
        }
        val kaomoji = when (error) {
            Error.RETROFIT -> "(╥﹏╥)"
            Error.EMPTY -> "Σ(°ロ°)"
        }

        Text(
            text = kaomoji,
            fontSize = 50.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.inverseSurface
        )
        Text(
            text = message,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.inverseSurface
        )

        Button(modifier = Modifier.padding(top = 50.dp), onClick = { retry() }) {
            Text(
                text = stringResource(id = R.string.retry),
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

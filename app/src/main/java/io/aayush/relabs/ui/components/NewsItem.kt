package io.aayush.relabs.ui.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import io.aayush.relabs.R
import io.aayush.relabs.ui.extensions.shimmer

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    thumbnail: String = "",
    headline: String = "",
    description: String = "",
    author: String = "",
    date: String = "",
    onClicked: () -> Unit = {},
    loading: Boolean = false,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            )
            .padding(12.dp)
            .clickable { onClicked() },
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .placeholder(R.drawable.ic_landscape)
                .crossfade(true)
                .build(),
            imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }.build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(158.0.dp)
                .clip(RoundedCornerShape(20.dp))
                .shimmer(loading)
        )
        Text(
            text = headline,
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(loading)
        )
        if (description.isNotBlank()) {
            Text(
                text = description,
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(loading)
            )
        }
        Column {
            Text(
                text = author,
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(loading)
            )
            Text(
                text = date,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(loading)
            )
        }
    }
}
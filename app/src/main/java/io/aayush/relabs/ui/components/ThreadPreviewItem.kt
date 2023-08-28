package io.aayush.relabs.ui.components

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import io.aayush.relabs.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ThreadPreviewItem(
    modifier: Modifier = Modifier,
    avatarURL: String = "",
    title: String = "",
    author: String = "",
    totalReplies: Int = 0,
    views: Int = 0,
    lastReplyDate: Int = 0,
    forum: String = "",
    unread: Boolean = false,
    onClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClicked() },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarURL.ifBlank { R.drawable.ic_account })
                .placeholder(R.drawable.ic_account)
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
                .requiredSize(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = if (unread) FontWeight.Medium else FontWeight.Light
            )
            Text(
                text = stringResource(
                    id = R.string.author_replies_creationDate,
                    author,
                    NumberFormat.getInstance(Locale.getDefault()).format(totalReplies),
                    NumberFormat.getInstance(Locale.getDefault()).format(views)
                ),
                fontSize = 14.sp,
                fontWeight = if (unread) FontWeight.Normal else FontWeight.Light
            )
            Text(text = forum, fontSize = 13.sp, fontWeight = FontWeight.Light)
        }
    }
}

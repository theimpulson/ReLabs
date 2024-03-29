package io.aayush.relabs.ui.components

import android.os.Build
import android.text.format.DateUtils
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
import io.aayush.relabs.ui.extensions.shimmer
import java.text.NumberFormat
import java.util.Date
import java.util.Locale

@Composable
fun ThreadPreviewItem(
    modifier: Modifier = Modifier,
    avatarURL: String = "",
    title: String = "",
    author: String? = "",
    totalReplies: Int = 0,
    views: Int = 0,
    lastReplyDate: Long = 0,
    forum: String = "",
    unread: Boolean = false,
    onClicked: () -> Unit = {},
    loading: Boolean = false,
    lastReplyAuthor: String = ""
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
                .shimmer(loading)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = if (unread) FontWeight.Medium else FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(loading)
            )
            Text(
                text = if (author == null || author.isNotBlank()) {
                    stringResource(
                        id = R.string.author_replies_creationDate,
                        author ?: stringResource(id = R.string.guest),
                        NumberFormat.getInstance(Locale.getDefault()).format(totalReplies),
                        NumberFormat.getInstance(Locale.getDefault()).format(views)
                    )
                } else {
                    String()
                },
                fontSize = 14.sp,
                fontWeight = if (unread) FontWeight.Normal else FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth(if (author.isNullOrBlank()) 0.75F else 1F)
                    .shimmer(loading)
            )
            Text(
                text = if (forum.isNotBlank()) {
                    forum
                } else if (lastReplyDate != 0L && lastReplyAuthor.isNotBlank()) {
                    stringResource(
                        id = R.string.last_reply,
                        lastReplyAuthor,
                        DateUtils.getRelativeTimeSpanString(
                            lastReplyDate,
                            Date().time,
                            DateUtils.MINUTE_IN_MILLIS
                        ).toString().lowercase()
                    )
                } else {
                    String()
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth(if (loading) 0.5F else 1F)
                    .shimmer(loading)
            )
        }
    }
}

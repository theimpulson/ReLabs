package io.aayush.relabs.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import coil.compose.SubcomposeAsyncImage
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
    onClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarURL.ifBlank { R.drawable.ic_account })
                .placeholder(R.drawable.ic_account)
                .crossfade(true)
                .build(),
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
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Text(
                text = stringResource(
                    id = R.string.author_replies_creationDate,
                    author,
                    totalReplies,
                    NumberFormat.getInstance(Locale.getDefault()).format(views)
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Text(text = forum, fontSize = 13.sp, fontWeight = FontWeight.Light)
        }
    }
}

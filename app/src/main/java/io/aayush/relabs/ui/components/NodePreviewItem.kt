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
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun NodePreviewItem(
    modifier: Modifier = Modifier,
    title: String = "",
    company: String = "",
    threads: Int = 0,
    lastUpdated: Long = 0,
    lastThreadTitle: String = "",
    unread: Boolean = false,
    onClicked: () -> Unit = {},
    loading: Boolean = false,
    iconURL: String = ""
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
                .data(iconURL.ifBlank { R.drawable.ic_phone })
                .placeholder(R.drawable.ic_phone)
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
                .requiredSize(48.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmer(loading)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
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
                text = if (company.isNotBlank()) {
                    stringResource(
                        id = R.string.company_threads_lastPostDate,
                        company,
                        NumberFormat.getInstance(Locale.getDefault()).format(threads),
                        if (lastUpdated != 0L) {
                            DateUtils.getRelativeTimeSpanString(
                                lastUpdated,
                                Date().time,
                                DateUtils.MINUTE_IN_MILLIS
                            ).toString().lowercase()
                        } else {
                            String()
                        }
                    )
                } else {
                    String()
                },
                fontSize = 14.sp,
                fontWeight = if (unread) FontWeight.Normal else FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth(if (company.isBlank()) 0.75F else 1F)
                    .shimmer(loading)
            )
            Text(
                text = if (lastThreadTitle.isNotBlank()) {
                    stringResource(id = R.string.last_thread, lastThreadTitle)
                } else {
                    String()
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth(if (lastThreadTitle.isBlank()) 0.5F else 1F)
                    .shimmer(loading)
            )
        }
    }
}

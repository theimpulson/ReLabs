package io.aayush.relabs.ui.components

import android.os.Build
import android.text.format.DateUtils
import android.text.util.Linkify
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.android.material.textview.MaterialTextView
import io.aayush.relabs.R
import io.aayush.relabs.network.data.post.Post
import io.aayush.relabs.ui.theme.XDAYellow
import io.aayush.relabs.utils.CoilImageGetter
import io.aayush.relabs.utils.DesignQuoteSpan
import io.aayush.relabs.utils.LinkTransformationMethod
import io.aayush.relabs.utils.formatBlockQuotes
import java.util.Date

@Composable
fun PostItem(
    post: Post,
    linkTransformationMethod: LinkTransformationMethod,
    designQuoteSpan: DesignQuoteSpan,
    isThreadOwner: Boolean = false,
    isThreadOpen: Boolean = true,
    reactionScore: Int = 0,
    reacted: Boolean = false,
    onReact: () -> Unit = {},
    onQuote: () -> Unit = {},
    onMultiQuote: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            )
    ) {
        val context = LocalContext.current

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.User?.avatar_urls?.values?.first() ?: R.drawable.ic_account_w)
                        .placeholder(R.drawable.ic_account_w)
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
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start)
                    ) {
                        Text(
                            text = post.User?.username ?: stringResource(id = R.string.guest),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (isThreadOwner) {
                            Text(
                                text = stringResource(id = R.string.op),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = XDAYellow
                            )
                        }
                    }
                    if (post.User?.user_title != null) {
                        Text(text = post.User.user_title, fontSize = 13.sp, color = Color.White)
                    }
                    Text(
                        text = DateUtils.getRelativeTimeSpanString(
                            post.post_date.toLong() * 1000L,
                            Date().time,
                            DateUtils.MINUTE_IN_MILLIS
                        ).toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
            AndroidView(
                factory = {
                    MaterialTextView(it).apply {
                        autoLinkMask = Linkify.WEB_URLS
                        linksClickable = true
                        transformationMethod = linkTransformationMethod
                        setLinkTextColor(Color.White.toArgb())
                    }
                },
                update = {
                    it.setTextColor(Color.White.toArgb())
                    it.text = HtmlCompat.fromHtml(
                        post.message_parsed
                            // XenForo doesn't applies line break after quote while vbulletin did
                            .replace("</blockquote>", "</blockquote><br />")
                            .replace("</blockquote><br /><br />", "</blockquote><br />"),
                        HtmlCompat.FROM_HTML_MODE_COMPACT,
                        CoilImageGetter(context),
                        null
                    ).formatBlockQuotes(designQuoteSpan)
                }
            )

            Column(
                modifier = Modifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (isThreadOpen) {
                        IconButton(onClick = { onQuote() }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_comment),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                        IconButton(onClick = { onMultiQuote() }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_forum),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                    IconButton(onClick = { onReact() }) {
                        Image(
                            painter = painterResource(
                                id = if (reacted) R.drawable.ic_like_filled else R.drawable.ic_like
                            ),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }

                if (reactionScore != 0) {
                    Text(
                        modifier = Modifier.padding(start = 13.dp, bottom = 10.dp),
                        text = if (reacted) {
                            if (reactionScore - 1 == 0) {
                                stringResource(id = R.string.you_reacted_to, reactionScore)
                            } else {
                                stringResource(id = R.string.you_and_reacted_to, reactionScore - 1)
                            }
                        } else {
                            stringResource(id = R.string.reacted_to, reactionScore)
                        },
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

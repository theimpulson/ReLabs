package io.aayush.relabs.ui.components

import android.content.Context
import android.net.Uri
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.URLSpan
import android.util.Log
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.prof.rssparser.Article
import io.aayush.relabs.R

private const val TAG = "RSSFeedItem"

@Composable
fun RSSFeedItem(context: Context, article: Article) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                try {
                    CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(context, Uri.parse(article.link))
                } catch (exception: Exception) {
                    Log.e(TAG, "Failed to open profile", exception)
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .requiredSize(72.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = article.title ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            AndroidView(
                modifier = Modifier.padding(top = 5.dp),
                factory = { context -> TextView(context) },
                update = { textView ->
                    textView.includeFontPadding = false
                    textView.text = HtmlCompat.fromHtml(article.content ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
                    textView.removeLinksUnderline()
                }
            )
            Text(
                text = stringResource(
                    id = R.string.posted_by,
                    article.author ?: ""
                ),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun TextView.removeLinksUnderline() {
    val spannable = SpannableString(text)
    for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
        spannable.setSpan(
            object : URLSpan(u.url) {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            },
            spannable.getSpanStart(u),
            spannable.getSpanEnd(u),
            0
        )
    }
    text = spannable
}

package io.aayush.relabs.ui.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Scale
import io.aayush.relabs.R
import io.aayush.relabs.network.data.user.User
import io.aayush.relabs.ui.theme.XDAYellow

@Composable
fun UserHeader(user: User) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("")
                .crossfade(true)
                .scale(Scale.FIT)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .height(150.dp)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(top = 90.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar?.data?.medium?.ifBlank { R.drawable.ic_account_w } ?: R.drawable.ic_account_w)
                    .placeholder(R.drawable.ic_account_w)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                imageLoader = ImageLoader.Builder(LocalContext.current)
                    .components {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build(),
                modifier = Modifier
                    .requiredSize(96.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = user.username, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(5.dp))
                if (user.isModerator) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_mod),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(XDAYellow)
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = user.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

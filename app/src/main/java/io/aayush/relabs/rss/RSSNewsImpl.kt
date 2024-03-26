package io.aayush.relabs.rss

import android.util.Log
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssItem
import io.aayush.relabs.rss.data.RssFeed
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class RSSNewsImpl @Inject constructor(
    private val parser: RssParser
) {

    private val TAG = RSSNewsImpl::class.java.simpleName

    suspend fun getFeed(rssFeed: RssFeed): Result<List<RssItem>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getRssChannel(rssFeed.source).items)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }
}

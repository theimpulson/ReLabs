package io.aayush.relabs.rss

import android.util.Log
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RSSNewsImpl @Inject constructor(
    private val parser: RssParser
) {

    private val TAG = RSSNewsImpl::class.java.simpleName

    companion object {
        private const val FEED_XDA_PORTAL = "https://www.xda-developers.com/feed/category/mobile/"
        private const val FEED_9TO5GOOGLE = "https://9to5google.com/feed/"
        private const val FEED_ANDROID_DEVS = "https://feeds.feedburner.com/blogspot/hsDu"
    }

    suspend fun getXDAPortalFeed(): Result<List<RssItem>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getRssChannel(FEED_XDA_PORTAL).items)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch xda portal rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }

    suspend fun get9to5GoogleFeed(): Result<List<RssItem>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getRssChannel(FEED_9TO5GOOGLE).items)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch 9to5Google rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }

    suspend fun getAndroidDevsFeed(): Result<List<RssItem>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getRssChannel(FEED_ANDROID_DEVS).items)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch android developers rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }
}

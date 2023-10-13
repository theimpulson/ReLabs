package io.aayush.relabs.rss

import android.util.Log
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RSSNewsImpl @Inject constructor(
    private val parser: Parser
) {

    private val TAG = RSSNewsImpl::class.java.simpleName

    companion object {
        private const val FEED_XDA_PORTAL = "https://www.xda-developers.com/feed/category/mobile/"
        private const val FEED_9TO5GOOGLE = "https://9to5google.com/feed/"
        private const val FEED_ANDROID_DEVS = "https://feeds.feedburner.com/blogspot/hsDu"
    }

    suspend fun getXDAPortalFeed(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getChannel(FEED_XDA_PORTAL).articles)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch xda portal rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }

    suspend fun get9to5GoogleFeed(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getChannel(FEED_9TO5GOOGLE).articles)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch 9to5Google rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }

    suspend fun getAndroidDevsFeed(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getChannel(FEED_ANDROID_DEVS).articles)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch android developers rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }
}

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
        private const val FEED_MOBILE = "https://www.xda-developers.com/feed/category/mobile/"
        private const val FEED_COMPUTING = "www.xda-developers.com/feed/category/computing/"
        private const val FEED_SMART_HOME = "www.xda-developers.com/feed/category/home/"
    }

    suspend fun getMobileFeed(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getChannel(FEED_MOBILE).articles)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch mobile rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }

    suspend fun getComputingFeed(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getChannel(FEED_COMPUTING).articles)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch mobile rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }

    suspend fun getSmartHomeFeed(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext Result.success(parser.getChannel(FEED_SMART_HOME).articles)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to fetch mobile rss feed!", exception)
                return@withContext Result.failure(exception)
            }
        }
    }
}

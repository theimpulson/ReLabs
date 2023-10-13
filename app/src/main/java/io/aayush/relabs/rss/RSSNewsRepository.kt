package io.aayush.relabs.rss

import com.prof.rssparser.Article
import javax.inject.Inject

class RSSNewsRepository @Inject constructor(
    private val rssNewsImpl: RSSNewsImpl
) {

    suspend fun getXDAPortalFeed(): Result<List<Article>> {
        return rssNewsImpl.getXDAPortalFeed()
    }

    suspend fun get9to5GoogleFeed(): Result<List<Article>> {
        return rssNewsImpl.get9to5GoogleFeed()
    }

    suspend fun getAndroidDevsFeed(): Result<List<Article>> {
        return rssNewsImpl.getAndroidDevsFeed()
    }
}

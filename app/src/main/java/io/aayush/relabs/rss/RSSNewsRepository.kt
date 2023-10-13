package io.aayush.relabs.rss

import com.prof18.rssparser.model.RssItem
import javax.inject.Inject

class RSSNewsRepository @Inject constructor(
    private val rssNewsImpl: RSSNewsImpl
) {

    suspend fun getXDAPortalFeed(): Result<List<RssItem>> {
        return rssNewsImpl.getXDAPortalFeed()
    }

    suspend fun get9to5GoogleFeed(): Result<List<RssItem>> {
        return rssNewsImpl.get9to5GoogleFeed()
    }

    suspend fun getAndroidDevsFeed(): Result<List<RssItem>> {
        return rssNewsImpl.getAndroidDevsFeed()
    }
}

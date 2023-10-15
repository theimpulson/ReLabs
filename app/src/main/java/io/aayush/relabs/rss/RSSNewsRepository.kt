package io.aayush.relabs.rss

import com.prof18.rssparser.model.RssItem
import io.aayush.relabs.rss.data.RssFeed
import javax.inject.Inject

class RSSNewsRepository @Inject constructor(
    private val rssNewsImpl: RSSNewsImpl
) {

    suspend fun getXDAPortalFeed(): Result<List<RssItem>> {
        return rssNewsImpl.getFeed(RssFeed.XDA)
    }

    suspend fun get9to5GoogleFeed(): Result<List<RssItem>> {
        return rssNewsImpl.getFeed(RssFeed.Google9To5)
    }

    suspend fun getAndroidDevsFeed(): Result<List<RssItem>> {
        return rssNewsImpl.getFeed(RssFeed.AndroidDevs)
    }

    suspend fun getArsTechFeed(): Result<List<RssItem>> {
        return rssNewsImpl.getFeed(RssFeed.ArsTech)
    }
}

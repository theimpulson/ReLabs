package io.aayush.relabs.rss

import com.prof.rssparser.Article
import javax.inject.Inject

class RSSNewsRepository @Inject constructor(
    private val rssNewsImpl: RSSNewsImpl
) {

    suspend fun getMobileFeed(): Result<List<Article>> {
        return rssNewsImpl.getMobileFeed()
    }

    suspend fun getComputingFeed(): Result<List<Article>> {
        return rssNewsImpl.getComputingFeed()
    }

    suspend fun getSmartHomeFeed(): Result<List<Article>> {
        return rssNewsImpl.getSmartHomeFeed()
    }
}

package io.aayush.relabs.rss.data

sealed class RssFeed(val source: String) {
    data object ArsTech : RssFeed("https://feeds.arstechnica.com/arstechnica/index")
    data object Google9To5 : RssFeed("https://9to5google.com/feed/")
    data object AndroidDevs : RssFeed("https://feeds.feedburner.com/blogspot/hsDu")
}

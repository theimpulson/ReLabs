package io.aayush.relabs.ui.screens.news

import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.rssparser.model.RssItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.rss.RSSNewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val rssNewsRepository: RSSNewsRepository,
    val customTabsIntent: CustomTabsIntent
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _xdaPortalFeed = MutableStateFlow<List<RssItem>>(emptyList())
    val xdaPortalFeed = _xdaPortalFeed.asStateFlow()

    private val _arsTechFeed = MutableStateFlow<List<RssItem>>(emptyList())
    val arsTechFeed = _arsTechFeed.asStateFlow()

    private val _google9to5Feed = MutableStateFlow<List<RssItem>>(emptyList())
    val google9to5Feed = _google9to5Feed.asStateFlow()

    private val _androidDevsFeed = MutableStateFlow<List<RssItem>>(emptyList())
    val androidDevsFeed = _androidDevsFeed.asStateFlow()

    fun getXDAPortalArticles() {
        if (!xdaPortalFeed.value.isNullOrEmpty()) return
        viewModelScope.launch {
            fetch {
                _xdaPortalFeed.value =
                    rssNewsRepository.getXDAPortalFeed().getOrDefault(emptyList())
            }
        }
    }

    fun get9to5GoogleArticles() {
        if (!google9to5Feed.value.isNullOrEmpty()) return
        viewModelScope.launch {
            fetch {
                _google9to5Feed.value =
                    rssNewsRepository.get9to5GoogleFeed().getOrDefault(emptyList())
            }
        }
    }

    fun getAndroidDevelopersArticles() {
        if (!androidDevsFeed.value.isNullOrEmpty()) return
        viewModelScope.launch {
            fetch {
                _androidDevsFeed.value =
                    rssNewsRepository.getAndroidDevsFeed().getOrDefault(emptyList())
            }
        }
    }

    fun getArsTechArticles() {
        if (!arsTechFeed.value.isNullOrEmpty()) return
        viewModelScope.launch {
            fetch {
                _arsTechFeed.value =
                    rssNewsRepository.getArsTechFeed().getOrDefault(emptyList())
            }
        }
    }

    private inline fun <T> fetch(block: () -> T): T? {
        return try {
            _loading.value = true
            block()
        } finally {
            _loading.value = false
        }
    }
}

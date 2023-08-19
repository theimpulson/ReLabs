package io.aayush.relabs.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.rss.RSSNewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val rssNewsRepository: RSSNewsRepository
) : ViewModel() {

    private val _mobileFeed = MutableStateFlow<List<Article>>(emptyList())
    val mobileFeed = _mobileFeed.asStateFlow()

    private val _computingFeed = MutableStateFlow<List<Article>>(emptyList())
    val computingFeed = _computingFeed.asStateFlow()

    private val _smartHomeFeed = MutableStateFlow<List<Article>>(emptyList())
    val smartHomeFeed = _smartHomeFeed.asStateFlow()

    fun getMobileArticles() {
        viewModelScope.launch {
            _mobileFeed.value = rssNewsRepository.getMobileFeed().getOrDefault(emptyList())
        }
    }

    fun getComputingArticles() {
        viewModelScope.launch {
            _computingFeed.value = rssNewsRepository.getComputingFeed().getOrDefault(emptyList())
        }
    }

    fun getSmartHomeArticles() {
        viewModelScope.launch {
            _smartHomeFeed.value = rssNewsRepository.getSmartHomeFeed().getOrDefault(emptyList())
        }
    }
}

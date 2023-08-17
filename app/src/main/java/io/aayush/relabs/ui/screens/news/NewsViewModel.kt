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

    private val _feed = MutableStateFlow<List<Article>>(emptyList())
    val feed = _feed.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            _feed.value = rssNewsRepository.getMobileFeed().getOrDefault(emptyList())
        }
    }
}

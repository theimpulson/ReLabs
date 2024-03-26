package io.aayush.relabs.ui.screens.threadpreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.thread.Thread
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class ThreadPreviewViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<Thread>>(PagingData.empty())
    val searchResults = _searchResults.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _shouldShowSearchResults = MutableStateFlow(false)
    val shouldShowSearchResults = _shouldShowSearchResults.asStateFlow()

    val isSearching = MutableStateFlow(false)

    private val _trendingThreads = MutableStateFlow<PagingData<Thread>>(PagingData.empty())
    val trendingThreads = _trendingThreads.asStateFlow()

    private val _watchedThreads = MutableStateFlow<PagingData<Thread>>(PagingData.empty())
    val watchedThreads = _watchedThreads.asStateFlow()

    init {
        getWatchedThreads()
        getTrendingThreads()
    }

    private fun getTrendingThreads() {
        viewModelScope.launch {
            xdaRepository.getThreads()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _trendingThreads.value = it
                }
        }
    }

    private fun getWatchedThreads() {
        viewModelScope.launch {
            xdaRepository.getWatchedThreads()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _watchedThreads.value = it
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) _shouldShowSearchResults.value = false
    }

    fun search(query: String) {
        _searchQuery.value = query
        _shouldShowSearchResults.value = query.isNotBlank()

        viewModelScope.launch {
            xdaRepository.getSearchResultsForThreads(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _searchResults.value = it
                }
        }
    }
}

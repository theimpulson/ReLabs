package io.aayush.relabs.ui.screens.threadpreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.thread.Thread
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadPreviewViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

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
}

package io.aayush.relabs.ui.screens.threadpreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.thread.Thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadPreviewViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _trendingThreads = MutableStateFlow<List<Thread>?>(emptyList())
    val trendingThreads = _trendingThreads.asStateFlow()

    private val _watchedThreads = MutableStateFlow<List<Thread>?>(emptyList())
    val watchedThreads = _watchedThreads.asStateFlow()

    fun getTrendingThreads() {
        if (!trendingThreads.value.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            fetch { _trendingThreads.value = xdaRepository.getThreads()?.threads }
        }
    }

    fun getWatchedThreads() {
        if (!watchedThreads.value.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            fetch { _watchedThreads.value = xdaRepository.getWatchedThreads()?.threads }
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

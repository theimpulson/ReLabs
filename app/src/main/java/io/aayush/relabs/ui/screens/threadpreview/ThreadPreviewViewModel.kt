package io.aayush.relabs.ui.screens.threadpreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.thread.Thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadPreviewViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository
) : ViewModel() {

    private val _trendingThreads = MutableStateFlow<List<Thread>?>(emptyList())
    val trendingThreads = _trendingThreads.asStateFlow()

    private val _watchedThreads = MutableStateFlow<List<Thread>?>(emptyList())
    val watchedThreads = _watchedThreads.asStateFlow()

    fun getTrendingThreads() {
        viewModelScope.launch(Dispatchers.IO) {
            _trendingThreads.value = xenforoRepository.getThreads()?.threads
        }
    }

    fun getWatchedThreads() {
        viewModelScope.launch(Dispatchers.IO) {
            _watchedThreads.value = xenforoRepository.getWatchedThreads()?.threads
        }
    }
}

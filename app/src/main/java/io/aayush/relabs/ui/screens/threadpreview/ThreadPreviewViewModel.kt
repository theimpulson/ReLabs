package io.aayush.relabs.ui.screens.threadpreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.thread.Thread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ThreadPreviewViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    fun getTrendingThreads(): Flow<PagingData<Thread>> {
        return xdaRepository.getThreads().cachedIn(viewModelScope)
    }

    fun getWatchedThreads(): Flow<PagingData<Thread>> {
        return xdaRepository.getWatchedThreads().cachedIn(viewModelScope)
    }
}

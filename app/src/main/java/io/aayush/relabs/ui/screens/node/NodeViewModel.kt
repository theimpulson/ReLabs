package io.aayush.relabs.ui.screens.node

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
class NodeViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    fun getThreads(nodeID: Int): Flow<PagingData<Thread>> {
        return xdaRepository.getThreadsByNode(nodeID).cachedIn(viewModelScope)
    }
}

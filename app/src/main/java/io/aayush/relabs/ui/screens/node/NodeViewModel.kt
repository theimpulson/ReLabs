package io.aayush.relabs.ui.screens.node

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
class NodeViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _threads = MutableStateFlow<PagingData<Thread>>(PagingData.empty())
    val threads = _threads.asStateFlow()

    fun getThreads(nodeID: Int) {
        viewModelScope.launch {
            xdaRepository.getThreadsByNode(nodeID)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _threads.value = it
                }
        }
    }
}

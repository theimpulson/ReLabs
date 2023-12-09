package io.aayush.relabs.ui.screens.node

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
class NodeViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _threads = MutableStateFlow<List<Thread>?>(emptyList())
    val threads = _threads.asStateFlow()

    fun getThreads(nodeID: Int) {
        if (!threads.value.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            fetch { _threads.value = xenforoRepository.getThreadsByNode(nodeID)?.threads }
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

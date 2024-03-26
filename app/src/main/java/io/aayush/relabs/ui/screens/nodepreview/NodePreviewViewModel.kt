package io.aayush.relabs.ui.screens.nodepreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.node.Node
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NodePreviewViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _inventory = MutableStateFlow<List<Node>?>(emptyList())
    val inventory = _inventory.asStateFlow()

    private val _watchedNodes = MutableStateFlow<List<Node>?>(emptyList())
    val watchedNodes = _watchedNodes.asStateFlow()

    fun getInventory() {
        if (!inventory.value.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            fetch { _inventory.value = xdaRepository.getInventory() }
        }
    }

    fun getWatchedNodes() {
        if (!watchedNodes.value.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            fetch { _watchedNodes.value = xdaRepository.getWatchedNodes() }
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

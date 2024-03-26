package io.aayush.relabs.ui.screens.nodepreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.node.Node
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class NodePreviewViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<Node>>(PagingData.empty())
    val searchResults = _searchResults.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _shouldShowSearchResults = MutableStateFlow(false)
    val shouldShowSearchResults = _shouldShowSearchResults.asStateFlow()

    val isSearching = MutableStateFlow(false)

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

    fun updateQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) _shouldShowSearchResults.value = false
    }

    fun search(query: String) {
        _searchQuery.value = query
        _shouldShowSearchResults.value = query.isNotBlank()

        viewModelScope.launch {
            xdaRepository.getSearchResultsForNodes(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _searchResults.value = it
                }
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

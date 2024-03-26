package io.aayush.relabs.ui.screens.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.network.data.post.PostInfo
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _alerts = MutableStateFlow<PagingData<UserAlert>>(PagingData.empty())
    val alerts = _alerts.asStateFlow()

    val postInfo = MutableStateFlow(PostInfo())

    init {
        getAlerts()
        markAllAlerts(viewed = true)
    }

    private fun getAlerts() {
        viewModelScope.launch {
            xdaRepository.getAlerts()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _alerts.value = it
                }
        }
    }

    fun markAllAlerts(read: Boolean? = null, viewed: Boolean? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            xdaRepository.markAllAlerts(read, viewed)
        }
    }

    fun getPostInfo(postID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postInfo.value = xdaRepository.getPostInfo(postID) ?: PostInfo()
        }
    }
}

package io.aayush.relabs.ui.screens.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.network.data.post.PostInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    val postInfo = MutableStateFlow(PostInfo())

    init {
        markAllAlerts(viewed = true)
    }

    fun getAlerts(): Flow<PagingData<UserAlert>> {
        return xdaRepository.getAlerts().cachedIn(viewModelScope)
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

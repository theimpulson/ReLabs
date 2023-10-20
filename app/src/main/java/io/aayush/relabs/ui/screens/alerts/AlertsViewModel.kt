package io.aayush.relabs.ui.screens.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.alert.UserAlert
import io.aayush.relabs.network.data.post.PostInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val postInfo = MutableStateFlow(PostInfo())

    private val _alerts = MutableStateFlow<List<UserAlert>?>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        getAlerts()
        markAllAlerts(viewed = true)
    }

    private fun getAlerts() {
        viewModelScope.launch(Dispatchers.IO) {
            fetch { _alerts.value = xenforoRepository.getAlerts()?.alerts }
        }
    }

    fun markAllAlerts(read: Boolean? = null, viewed: Boolean? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val markAlert = xenforoRepository.markAllAlerts(read, viewed)

            if (read == true && markAlert?.success == true) {
                getAlerts()
            }
        }
    }

    fun getPostInfo(postID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postInfo.value = xenforoRepository.getPostInfo(postID) ?: PostInfo()
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

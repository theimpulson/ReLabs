package io.aayush.relabs.ui.screens.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.alert.UserAlert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository
) : ViewModel() {

    private val _alerts = MutableStateFlow<List<UserAlert>?>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        getAlerts()
    }

    private fun getAlerts() {
        viewModelScope.launch(Dispatchers.IO) {
            _alerts.value = xenforoRepository.getAlerts()?.alerts
        }
    }
}

package io.aayush.relabs.ui.screens.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XDARepository
import io.aayush.relabs.network.data.user.Me
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val xdaRepository: XDARepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<Me?>(Me())
    val currentUser = _currentUser.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = xdaRepository.getCurrentUser()
        }
    }
}

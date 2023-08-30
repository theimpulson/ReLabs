package io.aayush.relabs.ui.screens.reply

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.post.PostReply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplyViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository
) : ViewModel() {

    private val _postReply = MutableStateFlow<PostReply?>(PostReply())
    val postReply = _postReply.asStateFlow()

    fun postReply(threadID: Int, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _postReply.value = xenforoRepository.postReply(threadID, message)
        }
    }
}

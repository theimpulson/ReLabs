package io.aayush.relabs.ui.screens.reply

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.aayush.relabs.R
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.post.PostReply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak") // false positive, see https://github.com/google/dagger/issues/3253
class ReplyViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val xenforoRepository: XenforoRepository
) : ViewModel() {

    private val _postReply = MutableStateFlow<PostReply?>(PostReply())
    val postReply = _postReply.asStateFlow()

    fun postReply(threadID: Int, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val footer = context.getString(R.string.sent_from, Build.MODEL)
            _postReply.value = xenforoRepository.postReply(threadID, "${message}$footer")
        }
    }
}

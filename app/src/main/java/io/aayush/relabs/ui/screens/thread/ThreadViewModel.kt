package io.aayush.relabs.ui.screens.thread

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.post.Post
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.utils.DesignQuoteSpan
import io.aayush.relabs.utils.LinkTransformationMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository,
    val linkTransformationMethod: LinkTransformationMethod,
    val designQuoteSpan: DesignQuoteSpan
) : ViewModel() {

    private val _threadInfo = MutableStateFlow<ThreadInfo?>(ThreadInfo())
    val threadInfo = _threadInfo.asStateFlow()

    private val _posts = mutableStateListOf<List<Post>?>()
    val posts = _posts

    fun getThreadInfo(threadID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = xenforoRepository.getThreadInfo(threadID, with_posts = true)
            _threadInfo.value = response
            _posts.add(response?.posts)

            // Insert empty lists to allow replacing them with appropriate object when required
            response?.pagination?.last_page?.let { _posts.addAll((1..it).map { emptyList() }) }
        }
    }

    fun getPosts(page: Int) {
        val threadID = _threadInfo.value?.thread?.thread_id
        if (threadID != null && threadID != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = xenforoRepository.getThreadInfo(
                    threadID,
                    with_posts = true,
                    page = page
                )
                _posts[page - 1] = response?.posts
            }
        }
    }
}

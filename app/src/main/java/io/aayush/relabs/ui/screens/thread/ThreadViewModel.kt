package io.aayush.relabs.ui.screens.thread

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.aayush.relabs.R
import io.aayush.relabs.network.XenforoRepository
import io.aayush.relabs.network.data.post.Post
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.network.data.react.React
import io.aayush.relabs.network.data.thread.ThreadInfo
import io.aayush.relabs.utils.DesignQuoteSpan
import io.aayush.relabs.utils.LinkTransformationMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak") // false positive, see https://github.com/google/dagger/issues/3253
class ThreadViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val xenforoRepository: XenforoRepository,
    val linkTransformationMethod: LinkTransformationMethod,
    val designQuoteSpan: DesignQuoteSpan
) : ViewModel() {

    val postsToQuote = mutableStateListOf<Post>()

    private val _postReply = MutableStateFlow<PostReply?>(PostReply())
    val postReply = _postReply.asStateFlow()

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

    fun reactToPost(page: Int, post: Post) {
        viewModelScope.launch {
            val react = xenforoRepository.postReact(post.post_id, React.Like)
            if (react?.success == true) getPosts(page + 1)
        }
    }

    fun postReply(threadID: Int, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val header = postsToQuote.map {
                context.getString(
                    R.string.quote,
                    it.User?.username,
                    it.post_id,
                    it.user_id,
                    it.message
                )
            }
            val footer = context.getString(R.string.sent_from, Build.MODEL)
            _postReply.value =
                xenforoRepository.postReply(threadID, "${header.joinToString("")}${message}$footer")
        }
    }

    fun markThreadAsRead(threadID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            xenforoRepository.markThreadAsRead(threadID)
        }
    }
}

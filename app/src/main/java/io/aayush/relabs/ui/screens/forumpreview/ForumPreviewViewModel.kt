package io.aayush.relabs.ui.screens.forumpreview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.aayush.relabs.network.XenforoRepository
import javax.inject.Inject

@HiltViewModel
class ForumPreviewViewModel @Inject constructor(
    private val xenforoRepository: XenforoRepository
) : ViewModel()

package io.aayush.relabs.network.data.post

import io.aayush.relabs.network.data.common.DateTime

data class Attachment(
    val canView: Boolean = false,
    val created_at: DateTime = DateTime(),
    val file_size: Int = 0,
    val filename: String = String(),
    val height: Int? = 0,
    val id: Int = 0,
    val isImage: Boolean = false,
    val permalink: String = String(),
    val thumbnail: String = String(),
    val width: Int? = 0
)

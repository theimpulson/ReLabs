package io.aayush.relabs.network.data.post

data class Attachment(
    val attach_date: Int = 0,
    val attachment_id: Int = 0,
    val content_id: Int = 0,
    val content_type: String = String(),
    val direct_url: String = String(),
    val file_size: Int = 0,
    val filename: String = String(),
    val height: Int = 0,
    val is_audio: Boolean = false,
    val is_video: Boolean = false,
    val thumbnail_url: String = String(),
    val view_count: Int = 0,
    val width: Int = 0
)

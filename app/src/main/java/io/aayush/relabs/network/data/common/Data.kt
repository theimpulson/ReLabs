package io.aayush.relabs.network.data.common

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

data class Data(
    val background_color: String? = String(),
    val large: String = String(),
    val medium: String = String(),
    val small: String = String(),
    val xlarge: String = String(),
    val text: String? = String(),
    val text_color: String? = String()
) {
    val hexBackgroundColor: Color get() = Color(background_color!!.toColorInt())
    val hexTextColor: Color get() = Color(text_color!!.toColorInt())
}

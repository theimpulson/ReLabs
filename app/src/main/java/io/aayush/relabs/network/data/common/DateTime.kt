package io.aayush.relabs.network.data.common

import java.text.SimpleDateFormat
import java.util.Locale

data class DateTime(
    val atomic: String = String(), // 2024-01-11T21:26:49+05:30
    val formatted: String = String() // Jan 11, 2024 at 3:56 PM
) {
    val long: Long
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())
            return sdf.parse(atomic)!!.time
        }
}

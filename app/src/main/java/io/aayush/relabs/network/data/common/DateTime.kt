package io.aayush.relabs.network.data.common

import java.text.SimpleDateFormat
import java.util.Locale

data class DateTime(
    val atomic: String = "1970-01-01T00:00:00+00:00",
    val formatted: String = "January 1, 1970 at 0:00 AM"
) {
    val long: Long
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())
            return sdf.parse(atomic)!!.time
        }
}

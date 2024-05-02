package io.aayush.relabs.network.data.devices

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Devices(
    val devices: List<Device> = listOf()
)

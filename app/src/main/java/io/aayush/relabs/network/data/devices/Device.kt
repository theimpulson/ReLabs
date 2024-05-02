package io.aayush.relabs.network.data.devices

import com.squareup.moshi.JsonClass
import io.aayush.relabs.network.data.node.Node

@JsonClass(generateAdapter = true)
data class Device(
    val Node: Node = Node(),
    val description: String = String(),
    val device_id: Int = 0,
    val name: String = String(),
    val view_url: String = String()
)

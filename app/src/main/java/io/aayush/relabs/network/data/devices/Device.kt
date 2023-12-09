package io.aayush.relabs.network.data.devices

import io.aayush.relabs.network.data.node.Node

data class Device(
    val Node: Node,
    val description: String,
    val device_id: Int,
    val name: String,
    val view_url: String
)

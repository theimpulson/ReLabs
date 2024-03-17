package io.aayush.relabs.network.data.node

data class Node(
    val depth: Int = 0,
    val description: String = String(),
    val display_order: Int = 0,
    val id: Int = 0,
    val kind: Kind = Kind.FORUM,
    val lft: Int = 0,
    val node_data: NodeData = NodeData(),
    val node_type_data: NodeTypeData = NodeTypeData(),
    val breadcrumb_data: Map<Int, Breadcrumb> = emptyMap(),
    val rgt: Int = 0,
    val title: String = String(),
    val xda: XDA = XDA()
)

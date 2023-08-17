package io.aayush.relabs.network.data.node

data class Node(
    val breadcrumbs: List<Breadcrumb> = emptyList(),
    val description: String = String(),
    val display_in_list: Boolean = false,
    val display_order: Int = 0,
    val node_id: Int = 0,
    val node_name: String = String(),
    val node_type_id: String = String(),
    val parent_node_id: Int = 0,
    val title: String = String(),
    val type_data: TypeData = TypeData(),
    val view_url: String = String()
)

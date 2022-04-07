package com.rulhouse.ruler.feature_node.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

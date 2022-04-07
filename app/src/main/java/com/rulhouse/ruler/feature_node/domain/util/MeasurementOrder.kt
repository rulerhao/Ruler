package com.rulhouse.ruler.feature_node.domain.util

sealed class MeasurementOrder (val orderType: OrderType) {
    class Title(orderType: OrderType): MeasurementOrder(orderType)
    class Date(orderType: OrderType): MeasurementOrder(orderType)

    fun copy(orderType: OrderType): MeasurementOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }
}

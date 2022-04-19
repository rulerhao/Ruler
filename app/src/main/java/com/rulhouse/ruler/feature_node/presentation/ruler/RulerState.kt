package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.util.MeasurementOrder
import com.rulhouse.ruler.feature_node.domain.util.OrderType

data class RulerState(
    val notes: List<Measurement> = emptyList(),
    val noteOrder: MeasurementOrder = MeasurementOrder.Date(OrderType.Descending),
    val isSystemBarVisible: Boolean = false,
    val scale: RulerScale = RulerScale.Centimeter,
    val measurements: List<Measurement> = emptyList(),
)
package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.util.MeasurementOrder
import com.rulhouse.ruler.feature_node.domain.util.OrderType
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Size

data class RulerState(
    val measurements: List<Measurement> = emptyList(),
    val measureOrder: MeasurementOrder = MeasurementOrder.Date(OrderType.Descending),
    val isSystemBarVisible: Boolean = false,
    val scale: RulerScale = RulerScale.Centimeter,
    val scaleAreaSize: Size = Size(200f, 200f)
)
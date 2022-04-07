package com.rulhouse.ruler.feature_node.domain.use_case

import com.rulhouse.ruler.feature_node.domain.util.MeasurementOrder
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.repository.MeasurementRepository
import com.rulhouse.ruler.feature_node.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMeasurements (
    private val repository: MeasurementRepository
) {
    operator fun invoke(
        noteOrder: MeasurementOrder = MeasurementOrder.Date(OrderType.Descending)
    ): Flow<List<Measurement>> {
        return repository.getMeasurements().map { Measurements ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is MeasurementOrder.Title -> Measurements.sortedBy { it.title.lowercase() }
                        is MeasurementOrder.Date -> Measurements.sortedBy { it.timeStamp }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is MeasurementOrder.Title -> Measurements.sortedByDescending { it.title.lowercase() }
                        is MeasurementOrder.Date -> Measurements.sortedByDescending { it.timeStamp }
                    }
                }
            }
        }
    }
}
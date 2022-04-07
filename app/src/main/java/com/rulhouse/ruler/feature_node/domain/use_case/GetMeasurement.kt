package com.rulhouse.ruler.feature_node.domain.use_case

import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.repository.MeasurementRepository

class GetMeasurement(
    private val repository: MeasurementRepository
) {
    suspend operator fun invoke(id: Int): Measurement? {
        return repository.getMeasurementById(id)
    }
}
package com.rulhouse.ruler.feature_node.domain.repository

import com.rulhouse.ruler.feature_node.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    fun getMeasurements(): Flow<List<Measurement>>

    suspend fun getMeasurementById(id: Int): Measurement?

    suspend fun insertMeasurement(measurement: Measurement)

    suspend fun deleteMeasurement(measurement: Measurement)
}
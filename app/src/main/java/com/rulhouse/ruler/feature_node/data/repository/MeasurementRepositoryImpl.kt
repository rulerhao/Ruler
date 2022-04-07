package com.rulhouse.ruler.feature_node.data.repository

import com.rulhouse.ruler.feature_node.data.data_source.MeasurementDao
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow

class MeasurementRepositoryImpl (
    private val dao: MeasurementDao
) : MeasurementRepository {

    override fun getMeasurements(): Flow<List<Measurement>> {
        return dao.getMeasurements()
    }

    override suspend fun getMeasurementById(id: Int): Measurement? {
        return dao.getMeasurementById(id)
    }

    override suspend fun insertMeasurement(measurement: Measurement) {
        dao.insertMeasurement(measurement)
    }

    override suspend fun deleteMeasurement(measurement: Measurement) {
        dao.deleteMeasurement(measurement)
    }
}
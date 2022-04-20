package com.rulhouse.ruler.feature_node.domain.use_case


import com.rulhouse.ruler.feature_node.domain.model.InvalidMeasurementException
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.repository.MeasurementRepository

class AddMeasurement (
    private val repository: MeasurementRepository
) {
    @Throws(InvalidMeasurementException::class)
    suspend operator fun invoke(measurement: Measurement) {
//        if(measurement.title.isBlank()) {
//            throw InvalidMeasurementException("The title of the note can't be empty.")
//        }
        repository.insertMeasurement(measurement)
    }
}
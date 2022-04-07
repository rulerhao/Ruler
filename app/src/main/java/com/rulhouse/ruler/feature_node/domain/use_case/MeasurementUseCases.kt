package com.rulhouse.ruler.feature_node.domain.use_case

import com.rulhouse.ruler.feature_node.domain.use_case.AddMeasurement
import com.rulhouse.ruler.feature_node.domain.use_case.DeleteMeasurement
import com.rulhouse.ruler.feature_node.domain.use_case.GetMeasurement
import com.rulhouse.ruler.feature_node.domain.use_case.GetMeasurements

data class MeasurementUseCases (
    val getMeasurements: GetMeasurements,
    val deleteMeasurement: DeleteMeasurement,
    val addMeasurement: AddMeasurement,
    val getMeasurement: GetMeasurement
)

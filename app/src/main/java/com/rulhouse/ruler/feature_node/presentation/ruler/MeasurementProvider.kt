package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.domain.model.Measurement

object MeasurementProvider {
    val measurementList = listOf(
        Measurement(
            id = 1,
            title = "first",
            width = 50f,
            height = 100f,
            timeStamp = 1649745127385
        ),
        Measurement(
            id = 2,
            title = "second",
            width = 150f,
            height = 250f,
            timeStamp = 1649745127393
        ),
        Measurement(
            id = 3,
            title = "third",
            width = 70f,
            height = 30f,
            timeStamp = 1649745127452
        )
    )
}
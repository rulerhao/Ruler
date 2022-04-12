package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.domain.model.Measurement

object MeasurementProvider {
    val measurementList = listOf(
        Measurement(
            id = 1,
            title = "first",
            width = 50,
            height = 100,
            timeStamp = 1649745127385
        ),
        Measurement(
            id = 2,
            title = "second",
            width = 150,
            height = 250,
            timeStamp = 1649745127393
        ),
        Measurement(
            id = 3,
            title = "third",
            width = 70,
            height = 30,
            timeStamp = 1649745127452
        )
    )
}
package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.domain.model.Measurement

object MeasurementProvider {
    val measurementList = listOf(
        Measurement(
            id = 1,
            title = "first",
            width = 12.5f,
            height = 15.5f,
            timeStamp = 1649745127385
        ),
        Measurement(
            id = 2,
            title = "second",
            width = 11.7f,
            height = 19.1f,
            timeStamp = 1649745127393
        ),
        Measurement(
            id = 3,
            title = "third",
            width = 12.7f,
            height = 13.6f,
            timeStamp = 1649745127452
        )
    )
}
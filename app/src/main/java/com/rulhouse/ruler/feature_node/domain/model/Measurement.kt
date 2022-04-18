package com.rulhouse.ruler.feature_node.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Measurement(
    val title: String,
    val width: Float,
    val height: Float,
    val timeStamp: Long,
    @PrimaryKey val id: Int? = null
) {
}

class InvalidMeasurementException(message: String): Exception(message)
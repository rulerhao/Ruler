package com.rulhouse.ruler.feature_node.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rulhouse.ruler.feature_node.domain.model.Measurement

@Database (
    entities = [Measurement::class],
    version = 1
        )
abstract class MeasurementDataBase : RoomDatabase() {

    abstract val measurementDao: MeasurementDao

    companion object {
        const val DATABASE_NAME = "measurements_db"
    }
}
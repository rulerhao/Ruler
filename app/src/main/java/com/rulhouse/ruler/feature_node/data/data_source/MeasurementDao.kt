package com.rulhouse.ruler.feature_node.data.data_source

import androidx.room.*
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {

    @Query("SELECT * FROM measurement")
    fun getMeasurements(): Flow<List<Measurement>>

    @Query("SELECT * FROM measurement WHERE id = :id")
    suspend fun getMeasurementById(id: Int) : Measurement?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(note: Measurement)

    @Delete
    suspend fun deleteMeasurement(note: Measurement)
}
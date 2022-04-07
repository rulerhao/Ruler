package com.rulhouse.ruler.di

import android.app.Application
import androidx.room.Room
import com.rulhouse.ruler.feature_node.data.data_source.MeasurementDataBase
import com.rulhouse.ruler.feature_node.data.repository.MeasurementRepositoryImpl
import com.rulhouse.ruler.feature_node.domain.repository.MeasurementRepository
import com.rulhouse.ruler.feature_node.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMeasurementDatabase(app: Application): MeasurementDataBase {
        return Room.databaseBuilder(
            app,
            MeasurementDataBase::class.java,
            MeasurementDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMeasurementRepository(db: MeasurementDataBase): MeasurementRepository {
        return MeasurementRepositoryImpl(db.measurementDao)
    }

    @Provides
    @Singleton
    fun provideMeasurementUseCases(repository: MeasurementRepository): MeasurementUseCases {
        return MeasurementUseCases(
            getMeasurements = GetMeasurements(repository),
            deleteMeasurement = DeleteMeasurement(repository),
            addMeasurement = AddMeasurement(repository),
            getMeasurement = GetMeasurement(repository)
        )
    }
}
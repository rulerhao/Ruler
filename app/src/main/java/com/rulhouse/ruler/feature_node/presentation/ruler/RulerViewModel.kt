package com.rulhouse.ruler.feature_node.presentation.ruler

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rulhouse.ruler.feature_node.domain.model.InvalidMeasurementException
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.use_case.MeasurementUseCases
import com.rulhouse.ruler.feature_node.domain.util.MeasurementOrder
import com.rulhouse.ruler.feature_node.domain.util.OrderType
import com.rulhouse.ruler.feature_node.shared_pref.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalMaterialApi::class)
class RulerViewModel @Inject constructor(
    application: Application,
    private val measurementUseCases: MeasurementUseCases,
) : AndroidViewModel(application) {
    private val _state = mutableStateOf(RulerState())
    val state: State<RulerState> = _state

    private val _drawerState = mutableStateOf(
        BottomDrawerState(BottomDrawerValue.Closed)
    )
    val drawerState: State<BottomDrawerState> = _drawerState

    private var recentlyDeletedNote: Measurement? = null
    // Flow
    private val _eventFlow = MutableSharedFlow<RulerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Flow
    private val _uiEventFlow = MutableSharedFlow<RulerUiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private var currentMeasurementId: Int? = null

    private var getNotesJob: Job? = null

    init {
        getMeasurements(MeasurementOrder.Date(OrderType.Descending))
        getScale(application.applicationContext)
        getScaleAreaSize(application.applicationContext)
    }

    fun onEvent(event: RulerEvent) {
        viewModelScope.launch {
            when (event) {
                is RulerEvent.DeleteMeasurement -> {
                    viewModelScope.launch {
                        try {
                            measurementUseCases.deleteMeasurement(event.measurement)
                            recentlyDeletedNote = event.measurement
                            _uiEventFlow.emit(RulerUiEvent.DeleteMeasurement)
                        }
                        catch (e: InvalidMeasurementException) {
                        }
                    }
                }
                is RulerEvent.SetScale -> {
                    _state.value = state.value.copy(
                        scale = event.scale
                    )
                    _eventFlow.emit(
                        RulerEvent.StartChangeScaleAnimation(
                            scale = event.scale
                        )
                    )
                }
                is RulerEvent.RestoreNote -> {
                    viewModelScope.launch thisLaunch@ {
                        measurementUseCases.addMeasurement(recentlyDeletedNote ?: return@thisLaunch)
                        recentlyDeletedNote = null
                    }
                }
                is RulerEvent.ToggleSaveDrawer -> {
                    _drawerState.value = when(_drawerState.value.currentValue) {
                        BottomDrawerValue.Closed -> {
                            BottomDrawerState(BottomDrawerValue.Expanded)
                        }
                        BottomDrawerValue.Expanded -> {
                            BottomDrawerState(BottomDrawerValue.Closed)
                        }
                        else -> {
                            _drawerState.value
                        }
                    }
                }
                is RulerEvent.SwitchScale -> {
                    _state.value = state.value.copy(
                        scale = when(_state.value.scale) {
                            RulerScale.Centimeter -> {
                                RulerScale.Inch
                            }
                            RulerScale.Inch -> {
                                RulerScale.Centimeter
                            }
                        }
                    )
                    SharedPref.setLengthScale(_state.value.scale, getApplication<Application>().applicationContext)
                }
                is RulerEvent.SaveMeasurement -> {
                    viewModelScope.launch {
                        try {
                            measurementUseCases.addMeasurement(
                                Measurement(
                                    title = event.title,
                                    width = event.size.width,
                                    height = event.size.height,
                                    timeStamp = System.currentTimeMillis(),
                                    id = currentMeasurementId
                                )
                            )
                            _uiEventFlow.emit(
                                RulerUiEvent.SaveMeasurement
                            )
                        }
                        catch (e: InvalidMeasurementException) {
                        }
                    }
                }
                is RulerEvent.EditMeasurement -> {
                    viewModelScope.launch {
                        try {
                            measurementUseCases.addMeasurement(
                                event.measurement
                            )
                        }
                        catch (e: InvalidMeasurementException) {
                        }
                    }
                }
                is RulerEvent.ChangeScaleAreaSize -> {
                    _state.value = _state.value.copy(
                        scaleAreaSize = event.size
                    )
                    SharedPref.setScaleAreaSize(_state.value.scaleAreaSize, getApplication<Application>().applicationContext)
                }
                else -> {}
            }
        }
    }

    private fun getMeasurements(measurementOrder: MeasurementOrder) {
        getNotesJob?.cancel()
        getNotesJob = measurementUseCases.getMeasurements(measurementOrder)
            .onEach { measurements ->
                _state.value = state.value.copy(
                    measurements = measurements,
                    measureOrder = measurementOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getScale(context: Context) {
        _state.value = state.value.copy(
            scale = SharedPref.getLengthScale(context = context)
        )
    }

    private fun getScaleAreaSize(context: Context) {
        _state.value = state.value.copy(
            scaleAreaSize = SharedPref.getScaleAreaSize(context = context)
        )
    }
}
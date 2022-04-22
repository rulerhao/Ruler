package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rulhouse.ruler.feature_node.domain.model.InvalidMeasurementException
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.use_case.MeasurementUseCases
import com.rulhouse.ruler.feature_node.domain.util.MeasurementOrder
import com.rulhouse.ruler.feature_node.domain.util.OrderType
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
    private val measurementUseCases: MeasurementUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(RulerState())
    val state: State<RulerState> = _state

    private val _drawerState = mutableStateOf(
        BottomDrawerState(BottomDrawerValue.Closed)
    )
    val drawerState: State<BottomDrawerState> = _drawerState

    private val _lengthScale = mutableStateOf(RulerState(
        scale = RulerScale.Centimeter
    ))
    val lengthScale: State<RulerState> = _lengthScale

    private val _scaleAreaWidth = mutableStateOf(0.0f)
    val scaleAreaWidth: State<Float> = _scaleAreaWidth

    private val _scaleAreaHeight = mutableStateOf(0.0f)
    val scaleAreaHeight: State<Float> = _scaleAreaHeight

    // Flow
    private val _eventFlow = MutableSharedFlow<RulerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMeasurementId: Int? = null

    private var getNotesJob: Job? = null

    init {
        getMeasurements(MeasurementOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: RulerEvent) {
        viewModelScope.launch {
            when (event) {
                is RulerEvent.DeleteMeasurement -> {
                    viewModelScope.launch {
                        measurementUseCases.deleteMeasurement(event.measurement)
                    }
                }
                is RulerEvent.SetScale -> {
                    _lengthScale.value = lengthScale.value.copy(
                        scale = event.scale
                    )
                    _eventFlow.emit(
                        RulerEvent.StartChangeScaleAnimation(
                            scale = event.scale
                        )
                    )
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
                    _lengthScale.value = lengthScale.value.copy(
                        scale = when(_lengthScale.value.scale) {
                            RulerScale.Centimeter -> {
                                RulerScale.Inch
                            }
                            RulerScale.Inch -> {
                                RulerScale.Centimeter
                            }
                        }
                    )
                }
                is RulerEvent.SaveMeasurement -> {
                    viewModelScope.launch {
                        try {
                            Log.d("testAddEditNote", "First")
                            measurementUseCases.addMeasurement(
                                Measurement(
                                    title = event.title,
                                    width = event.size.width,
                                    height = event.size.height,
                                    timeStamp = System.currentTimeMillis(),
                                    id = currentMeasurementId
                                )
                            )
                            Log.d("testAddEditNote", "SaveNote noteID = $currentMeasurementId")
//                            _eventFlow.emit(UiEvent.SaveNote)
                        }
                        catch (e: InvalidMeasurementException) {
//                            _eventFlow.emit(
//                                UiEvent.ShowSnackbar(
//                                    message = e.message ?: "Couldn't save note"
//                                )
//                            )
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
                    _scaleAreaWidth.value = event.size.width
                    _scaleAreaHeight.value = event.size.height
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
}
package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rulhouse.ruler.feature_node.domain.model.InvalidMeasurementException
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.domain.use_case.MeasurementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulerViewModel @Inject constructor(
    private val measurementUseCases: MeasurementUseCases,
) : ViewModel() {
    private val _lengthScale = mutableStateOf(RulerState(
        scale = RulerScale.Centimeter
    ))
    val lengthScale: State<RulerState> = _lengthScale

    // Flow
    private val _eventFlow = MutableSharedFlow<RulerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMeasurementId: Int? = null
    fun onEvent(event: RulerEvent) {
        viewModelScope.launch {
            when (event) {
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
                            measurementUseCases.addMeasurement(
                                Measurement(
                                    title = "Test Title",
                                    width = 50,
                                    height = 100,
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
                else -> {}
            }
        }
    }
}
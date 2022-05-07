package com.rulhouse.ruler.feature_node.presentation.ruler.scale_screen

import android.app.Application
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rulhouse.ruler.feature_node.domain.use_case.MeasurementUseCases
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerEvent
import com.rulhouse.ruler.methods.ScreenMethods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScaleScreenViewModel: ViewModel() {
    private val _state = mutableStateOf(ScaleScreenState())
    val state: State<ScaleScreenState> = _state

    fun onEvent(event: ScaleScreenEvent) {
        when (event) {
            is ScaleScreenEvent.OnDragStart -> {
                val positionTouchDown = event.position
                val middlePositionWhenTouchDown = Offset(
                    x = ((state.value.left + state.value.right) / 2).toFloat(),
                    y = ((state.value.top + state.value.bottom) / 2).toFloat(),
                )
                val onDragStartAtLeft = positionTouchDown.x <= middlePositionWhenTouchDown.x
                val onDragStartAtTop = positionTouchDown.y <= middlePositionWhenTouchDown.y
                _state.value = state.value.copy(
                    onDragStartAtLeft = onDragStartAtLeft,
                    onDragStartAtTop = onDragStartAtTop
                )
            }
            is ScaleScreenEvent.OnDrag -> {
                // check drag position to decide which side should be drag
                if (state.value.onDragStartAtLeft) {
                    var tempLeft = state.value.left + event.dragAmount.x.toInt()
                    if (tempLeft < 0) tempLeft = 0
                    _state.value = state.value.copy(
                        left = tempLeft
                    )
                } else {
                    var tempRight = state.value.right + event.dragAmount.x.toInt()
                    if (tempRight > ScreenMethods.getWidth(event.context)) tempRight = ScreenMethods.getWidth(event.context)
                    _state.value = state.value.copy(
                        right = tempRight
                    )
                }
                if (state.value.onDragStartAtTop) {
                    var tempTop = state.value.top + event.dragAmount.y.toInt()
                    if (tempTop < 0) tempTop = 0
                    _state.value = state.value.copy(
                        top = tempTop
                    )
                } else {
                    var tempBottom = state.value.bottom + event.dragAmount.y.toInt()
                    if (tempBottom > ScreenMethods.getHeight(event.context)) tempBottom =
                        ScreenMethods.getHeight(event.context)
                    _state.value = state.value.copy(
                        bottom = tempBottom
                    )
                }
            }
            is ScaleScreenEvent.OnDragEnd -> {
                // if left and right or top and bottom position changed, swap them to the right.
                if (state.value.left > state.value.right) {
                    val temp = state.value.right
                    _state.value = state.value.copy(
                        right = state.value.left,
                        left = temp
                    )
                }
                if (state.value.top > state.value.bottom) {
                    val temp = state.value.bottom
                    _state.value = state.value.copy(
                        bottom = state.value.top,
                        top = temp
                    )
                }
            }
            else -> {}
        }
    }
}
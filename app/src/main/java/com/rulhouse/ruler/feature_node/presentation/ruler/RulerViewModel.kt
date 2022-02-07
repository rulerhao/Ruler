package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulerViewModel @Inject constructor(
) : ViewModel() {
    private val _lengthScale = mutableStateOf(RulerState(
        scale = RulerScale.Centimeter
    ))
    val lengthScale: State<RulerState> = _lengthScale

    // Flow
    private val _eventFlow = MutableSharedFlow<RulerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
            }
        }
    }
}
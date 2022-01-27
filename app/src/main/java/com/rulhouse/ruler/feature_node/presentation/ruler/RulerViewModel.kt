package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class RulerViewModel @Inject constructor(
) : ViewModel() {
    private val _lengthScale = mutableStateOf(RulerState(
        scale = RulerScale.Centimeter
    ))
    val lengthScale: State<RulerState> = _lengthScale

    fun onEvent(event: RulerEvent) {
        when (event) {
            is RulerEvent.SetScale -> {
                _lengthScale.value = lengthScale.value.copy(
                    scale = event.scale
                )
            }
        }
    }
}
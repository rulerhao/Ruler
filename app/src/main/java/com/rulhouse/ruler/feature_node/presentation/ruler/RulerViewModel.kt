package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RulerViewModel @Inject constructor(
) : ViewModel() {
    private val _isSystemBarShow = mutableStateOf(RulerState(
        isSystemBarVisible = false
    ))
    val isSystemBarShow: State<RulerState> = _isSystemBarShow

    fun onEvent(event: RulerEvent) {
        when (event) {
            is RulerEvent.ToggleSystemBar -> {
                _isSystemBarShow.value = isSystemBarShow.value.copy(
                    isSystemBarVisible = !isSystemBarShow.value.isSystemBarVisible
                )
            }
        }
    }
}
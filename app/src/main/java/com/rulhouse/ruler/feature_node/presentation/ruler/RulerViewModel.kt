package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulerViewModel @Inject constructor(
) : ViewModel() {
    // Title
    private val _noteTitle = mutableStateOf(RulerState(
        isSystemBarShow = true
    ))
    val noteTitle: State<RulerState> = _noteTitle

    fun onEvent(event: RulerEvent) {
        when (event) {
            is RulerEvent.ToggleSystemBar -> {
                viewModelScope.launch {

                }
            }
        }
    }
}
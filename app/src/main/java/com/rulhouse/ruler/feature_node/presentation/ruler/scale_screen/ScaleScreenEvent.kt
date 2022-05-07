package com.rulhouse.ruler.feature_node.presentation.ruler.scale_screen

import android.content.Context
import androidx.compose.ui.geometry.Offset

sealed class ScaleScreenEvent {
    data class OnDragStart(val position: Offset): ScaleScreenEvent()
    data class OnDrag(val dragAmount: Offset, val context: Context): ScaleScreenEvent()
    object SwitchScale: ScaleScreenEvent()
    object OnDragEnd: ScaleScreenEvent()
}

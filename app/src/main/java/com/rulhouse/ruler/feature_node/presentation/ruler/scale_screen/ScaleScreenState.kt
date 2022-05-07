package com.rulhouse.ruler.feature_node.presentation.ruler.scale_screen

import androidx.compose.ui.geometry.Offset

data class ScaleScreenState(
    val left: Int = 200,
    val right: Int = 400,
    val top: Int = 300,
    val bottom: Int = 500,
    val positionTouchDown: Offset = Offset.Unspecified,
    val middlePositionWhenTouchDown: Offset = Offset.Unspecified,
    val onDragStartAtTop: Boolean = false,
    val onDragStartAtLeft: Boolean = false,
)

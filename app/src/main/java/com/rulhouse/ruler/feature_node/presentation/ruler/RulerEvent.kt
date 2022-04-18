package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.presentation.ruler.util.Size

sealed class RulerEvent {
    data class StartChangeScaleAnimation(val scale: RulerScale): RulerEvent()
    data class SetScale(val scale: RulerScale): RulerEvent()
    data class SaveMeasurement(val title: String, val size: Size): RulerEvent()
    data class ChangeScaleAreaSize(val size: Size): RulerEvent()
    object ToggleSaveDrawer: RulerEvent()
    object SwitchScale: RulerEvent()
}

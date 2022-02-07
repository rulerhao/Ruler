package com.rulhouse.ruler.feature_node.presentation.ruler

sealed class RulerEvent {
    data class StartChangeScaleAnimation(val scale: RulerScale): RulerEvent()
    data class SetScale(val scale: RulerScale): RulerEvent()
}

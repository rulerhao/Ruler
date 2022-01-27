package com.rulhouse.ruler.feature_node.presentation.ruler

sealed class RulerEvent {
    data class SetScale(val scale: RulerScale) : RulerEvent()
}

package com.rulhouse.ruler.feature_node.presentation.ruler

sealed class RulerUiEvent {
    data class SetScale(val scale: RulerScale): RulerUiEvent()
}
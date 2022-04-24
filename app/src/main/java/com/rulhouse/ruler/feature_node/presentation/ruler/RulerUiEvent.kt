package com.rulhouse.ruler.feature_node.presentation.ruler

import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Size

sealed class RulerUiEvent {
    data class ShowSnackbar(val message: String): RulerUiEvent()
    object DeleteMeasurement: RulerUiEvent()
    object SaveMeasurement: RulerUiEvent()

    enum class UiEvent {
        DeleteMeasurement,
        SaveMeasurement,
        None
    }
}

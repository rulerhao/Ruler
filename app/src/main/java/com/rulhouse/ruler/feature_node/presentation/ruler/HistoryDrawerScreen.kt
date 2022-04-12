package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryDrawerScreen(
    rulerViewModel: RulerViewModel = hiltViewModel()
) {
    val measurements = remember { MeasurementProvider.measurementList }
    val textState = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it }
    )
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp),

    ) {
        items(
            items = measurements,
            itemContent = {
                MeasurementListItem(
                    measurement = it,
                    lengthScale = rulerViewModel.lengthScale.value.scale
                )
            }
        )
    }
}
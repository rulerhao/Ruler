package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rulhouse.ruler.feature_node.domain.model.Measurement

@Composable
fun MeasurementListItem(
    measurement: Measurement,
    lengthScale: RulerScale
) {
    Column(
        modifier = Modifier
            .padding(
                vertical = 50.dp
            ),
        ) {
        Text(
            text = measurement.title,
            style = typography.h3
        )
        Row(
        ) {
            Text(
                text = "${measurement.width} x ${measurement.height}",
                style = typography.h4
            )
            Text(
                modifier = Modifier.align(alignment = Alignment.Bottom),
                text = "(${lengthScale})",
                style = typography.h6,
            )
        }
    }
}
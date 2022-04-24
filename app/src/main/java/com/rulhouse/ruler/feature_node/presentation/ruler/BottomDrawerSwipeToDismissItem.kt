package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.feature_node.domain.model.Measurement

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomDrawerSwipeToDismissItem(
    viewModel: RulerViewModel = hiltViewModel(),
    measurement: Measurement,
) {
    var unread by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd) unread = !unread
            else if (it == DismissValue.DismissedToStart) {
                viewModel.onEvent(RulerEvent.DeleteMeasurement(measurement))
            }
            false
        }
    )

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp),
        directions = setOf(
            DismissDirection.StartToEnd,
            DismissDirection.EndToStart
        ),
        dismissThresholds = { direction ->
            // threshold of swiping from left to right or right to left.
            FractionalThreshold(
                if (direction == DismissDirection.StartToEnd) 0.25f
                else 0.5f
            )
        },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                // if "FractionalThreshold" goes over 0.25f (which is StartToEnd's
                // threshold than trigger DismissedToEnd)
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.LightGray
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f
                else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value
            ) {
//                BottomDrawerItem(
//                    measurement = measurement,
//                    unread = unread
//                )
            }
        }
    )
}
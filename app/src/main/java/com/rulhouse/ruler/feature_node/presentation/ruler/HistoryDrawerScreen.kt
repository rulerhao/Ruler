package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Size

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryDrawerScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val measurements = remember { mutableStateListOf<Measurement>() }
    measurements.swapList(MeasurementProvider.measurementList)

    val textState = remember { mutableStateOf(TextFieldValue()) }

    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .padding(all = 32.dp)
            .background(color = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Row(

            ) {
                TextField(
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                )
                Button(
                    onClick = {
                        Log.d("TestTextView", "OnDone")
                        viewModel.onEvent(
                            RulerEvent.SaveMeasurement(
                                title = textState.value.text,
                                size = Size(
                                    viewModel.scaleAreaWidth.value,
                                    viewModel.scaleAreaHeight.value
                                )
                            )
                        )
                    }
                ) {

                }
            }
            LazyColumn(

            ) {
                items(measurements) { item ->
                    var unread by remember { mutableStateOf(false) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) unread = !unread
                            else if (it == DismissValue.DismissedToStart) {
                                measurements.remove(item)
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
                                Column {
                                    Text(
                                        text = item.title,
                                        fontSize = 50.sp,
                                        fontWeight = if (unread) FontWeight.Bold else null
                                    )
                                    Row {
                                        Spacer(
                                            modifier = Modifier
                                                .width(32.dp)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .align(alignment = Alignment.Bottom),
                                            text = "W",
                                            fontSize = 18.sp,
                                            fontWeight = if (unread) FontWeight.Bold else null,
                                        )
                                        Text(
                                            modifier = Modifier
                                                .width(100.dp)
                                                .align(alignment = Alignment.Bottom),
                                            text = item.width.toString(),
                                            fontSize = 40.sp,
                                            fontWeight = if (unread) FontWeight.Bold else null,
                                            textAlign = TextAlign.Right
                                        )
                                        Text(
                                            modifier = Modifier
                                                .width(60.dp)
                                                .align(alignment = Alignment.Bottom),
                                            text = when (viewModel.lengthScale.value.scale) {
                                                RulerScale.Inch -> "IN"
                                                RulerScale.Centimeter -> "CM"
                                            },
                                            fontSize = 18.sp,
                                            fontWeight = if (unread) FontWeight.Bold else null,
                                            textAlign = TextAlign.Right
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(32.dp)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .align(alignment = Alignment.Bottom),
                                            text = "H",
                                            fontSize = 18.sp,
                                            fontWeight = if (unread) FontWeight.Bold else null,
                                        )
                                        Text(
                                            modifier = Modifier
                                                .width(100.dp)
                                                .align(alignment = Alignment.Bottom),
                                            text = item.height.toString(),
                                            fontSize = 40.sp,
                                            fontWeight = if (unread) FontWeight.Bold else null,
                                            textAlign = TextAlign.Right
                                        )
                                        Text(
                                            modifier = Modifier
                                                .width(60.dp)
                                                .align(alignment = Alignment.Bottom),
                                            text = when (viewModel.lengthScale.value.scale) {
                                                RulerScale.Inch -> "IN"
                                                RulerScale.Centimeter -> "CM"
                                            },
                                            fontSize = 18.sp,
                                            fontWeight = if (unread) FontWeight.Bold else null,
                                            textAlign = TextAlign.Right
                                        )
                                    }
                                }
//                                ListItem(
//                                    text = {
//                                        Text(
//                                            item.title,
//                                            fontWeight = if (unread) FontWeight.Bold else null
//                                        )
//                                    },
////                                    secondaryText = { Text("Swipe me left or right!") }
//                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}
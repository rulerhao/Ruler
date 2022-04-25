package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.activity.LengthScaleChanger
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.methods.TimeMethods
import com.rulhouse.ruler.ui.theme.*
import kotlin.math.abs

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomDrawerItem(
    viewModel: RulerViewModel = hiltViewModel(),
    measurement: Measurement
) {
    val itemWidth = remember { mutableStateOf(0f) }

    val isEdit = remember { mutableStateOf(false) }

    val offsetX = remember { mutableStateOf(0f) }

    val iconAnimationFlag = remember { mutableStateOf(false) }
    val dragThreshold = 0.125f
    val percentageOfDrag = if (itemWidth.value == 0f) 0f
    else
        if (abs(offsetX.value) / (itemWidth.value * dragThreshold) > 1) 1f
        else abs(offsetX.value) / (itemWidth.value * dragThreshold)

    val cardBackgroundColor = remember { mutableStateOf(Color.White) }
    val innerCardSize = remember { mutableStateOf(IntSize(0, 0)) }
    Column(

    ) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            backgroundColor = cardBackgroundColor.value
        ) {
            BackGroundIconScreen(
                height = innerCardSize.value.height,
                iconAnimationFlag = iconAnimationFlag
            )
            Card(
                modifier = Modifier
                    .offset { IntOffset(x = offsetX.value.toInt(), y = 0) }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState {
                            offsetX.value = offsetX.value + it
                            val deleteColor = getPercentageColor(Color.Red, percentageOfDrag)
                            val editColor = getPercentageColor(Color.DarkGray, percentageOfDrag)
                            cardBackgroundColor.value = if (offsetX.value < 0) deleteColor
                            else editColor
                            iconAnimationFlag.value =
                                abs(offsetX.value) > itemWidth.value * dragThreshold
                        },
                        onDragStopped = {
                            if (abs(offsetX.value) > itemWidth.value * dragThreshold) {
                                if (offsetX.value > 0) {
                                    isEdit.value = true
                                } else {
                                    viewModel.onEvent(RulerEvent.DeleteMeasurement(measurement = measurement))
                                }
                            }
                            offsetX.value = 0f
                        }
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        itemWidth.value = layoutCoordinates.size.width.toFloat()
                    }
                    .onSizeChanged {
                        innerCardSize.value = it
                    },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    MeasurementScreen(measurement = measurement)
                }
            }
        }
        Divider(
            thickness = 1.dp
        )
    }
    if (isEdit.value) {
        BottomDrawerEditDialog(
            measurement = measurement,
            onDismissRequest = {
                isEdit.value = false
            },
            onOkClick = { title ->
                if (title.isNotBlank()) {
                    viewModel.onEvent(
                        RulerEvent.EditMeasurement(
                            Measurement(
                                title = title,
                                width = measurement.width,
                                height = measurement.height,
                                timeStamp = measurement.timeStamp,
                                id = measurement.id
                            )
                        )
                    )
                }
            }
        )
    }
}

fun getPercentageColor(color: Color, percentage: Float): Color {
    return Color(
        red = ((1 - (1 - color.red) * (percentage)) * 255).toInt(),
        green = ((1 - (1 - color.green) * percentage) * 255).toInt(),
        blue = ((1 - (1 - color.blue) * percentage) * 255).toInt(),
        alpha = 255
    )
}

@Composable
fun TimeScreen(
    modifier: Modifier,
    timeStamp: Long
) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(
            text = TimeMethods.getDateString(
                LocalContext.current,
                timeStamp
            )
        )
        Box(
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(
                text = TimeMethods.getTimeString(
                    LocalContext.current,
                    timeStamp
                )
            )
        }
    }
}

@Composable
fun MeasurementScreen(
    viewModel: RulerViewModel = hiltViewModel(),
    measurement: Measurement
) {
    val title = measurement.title.ifBlank { "No Title" }
    Column(
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(SecondaryDarkColor)
                .padding(8.dp),
            text = title,
            style = MaterialTheme.typography.h4,
            color = TextOnSColor,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row {
                Spacer(
                    modifier = Modifier
                        .width(32.dp)
                )
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom),
                    text = "%.2f".format(
                        if (viewModel.state.value.scale == RulerScale.Centimeter) measurement.width else LengthScaleChanger.cmToIn(
                            measurement.width
                        )
                    ),
                    style = MaterialTheme.typography.h2,
                    color = SecondaryColor,
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom),
                    text = when (viewModel.state.value.scale) {
                        RulerScale.Inch -> "IN"
                        RulerScale.Centimeter -> "CM"
                    },
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Right,
                    color = Color.Gray
                )
                Spacer(
                    modifier = Modifier
                        .width(32.dp)
                )
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom),
                    text = "%.2f".format(
                        if (viewModel.state.value.scale == RulerScale.Centimeter) measurement.height else LengthScaleChanger.cmToIn(
                            measurement.height
                        )
                    ),
                    style = MaterialTheme.typography.h2,
                    color = SecondaryColor,
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom),
                    text = when (viewModel.state.value.scale) {
                        RulerScale.Inch -> "IN"
                        RulerScale.Centimeter -> "CM"
                    },
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Right,
                    color = Color.Gray
                )
            }
            TimeScreen(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                timeStamp = measurement.timeStamp
            )
        }
    }
}

@Composable
fun BackGroundIconScreen(
    height: Int,
    iconAnimationFlag: MutableState<Boolean>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ScreenMethods.convertPixelToDp(height, LocalContext.current).dp)
    ) {
//        val visible = remember { mutableStateOf(true) }
//        val density = LocalDensity.current
//        if (iconAnimationFlag.value) {
//            AnimatedVisibility(
//                visible = iconAnimationFlag.value,
//                enter = slideInVertically {
//                    // Slide in from 40 dp from the top.
//                    with(density) { -40.dp.roundToPx() }
//                } + expandVertically(
//                    // Expand from the top.
//                    expandFrom = Alignment.Top
//                ) + fadeIn(
//                    // Fade in with the initial alpha of 0.3f.
//                    initialAlpha = 0.3f
//                ),
//                exit = slideOutVertically() + shrinkVertically() + fadeOut()
//            ) {
//                Text("Hello", Modifier.fillMaxWidth().height(200.dp))
//            }
//        }
        Card(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp),
            shape = CircleShape,
            backgroundColor = if (iconAnimationFlag.value) Color.White
            else Color.Transparent
        ) {
            Icon(
                Icons.Rounded.Edit,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp),
                contentDescription = "Edit",
                tint = Color.Black
            )
        }
        Card(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp),
            shape = CircleShape,
            backgroundColor = if (iconAnimationFlag.value) Color.White
            else Color.Transparent
        ) {
            Icon(
                Icons.Rounded.Delete,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp),
                contentDescription = "Delete",
                tint = Color.Black
            )
        }
    }
}
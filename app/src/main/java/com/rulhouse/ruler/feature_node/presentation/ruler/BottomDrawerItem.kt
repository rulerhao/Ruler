package com.rulhouse.ruler.feature_node.presentation.ruler

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.methods.TimeMethods
import java.util.*
import kotlin.math.abs

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomDrawerItem(
    viewModel: RulerViewModel = hiltViewModel(),
    measurement: Measurement,
    unread: Boolean
) {
    val pressState = remember { mutableStateOf(false) }
    val itemWidth = remember {mutableStateOf(0f) }
    val itemX = remember { mutableStateOf (0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = ScreenMethods.convertPixelToDp(itemX.value, LocalContext.current).dp)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart =  {

                    },
                    onDragEnd = {
                        if (abs(itemX.value) > itemWidth.value / 4) {
                            if (itemX.value > 0) {
                                Log.d("TestDragToDismiss", "focusRequester.requestFocus() A")
                            } else {
                                Log.d("TestDragToDismiss", "focusRequester.requestFocus() B")
                            }
                        } else {
                            itemX.value = 0f
                        }
                    }
                ) { change, dragAmount ->
                    itemX.value = itemX.value + dragAmount.x
                }
//                detectDragGestures(
//                    onDragStart = {
//
//                    },
//                    onDragEnd = {
//
//                    }
//                ) { change, dragAmount ->
////                    itemX.value = itemX.value + dragAmount.x
//                }
            }
            .onGloballyPositioned { layoutCoordinates ->
                itemWidth.value = layoutCoordinates.size.width.toFloat()
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val title = measurement.title.ifBlank { "No Title" }
            Column() {
                Text(
                    text = title,
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
                        text = "%.2f".format(measurement.width),
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
                        text = "%.2f".format(measurement.height),
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
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Text(
                    text = TimeMethods.getDateString(LocalContext.current, measurement.timeStamp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(
                        text = TimeMethods.getTimeString(LocalContext.current, measurement.timeStamp)
                    )
                }
            }
        }
    }
}
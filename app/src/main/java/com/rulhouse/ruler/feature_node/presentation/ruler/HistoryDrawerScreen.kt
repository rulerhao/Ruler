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
import androidx.compose.material.icons.rounded.Edit
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
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Size

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryDrawerScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd),
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Rounded.Edit,
                        contentDescription = "Edit",
                        tint = Color.Blue
                    )
                }
            }
            LazyColumn(

            ) {
                items(state.measurements) { measurement ->
                    BottomDrawerItem(
                        measurement = measurement,
                        unread = false
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
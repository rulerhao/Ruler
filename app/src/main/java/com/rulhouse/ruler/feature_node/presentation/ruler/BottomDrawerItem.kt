package com.rulhouse.ruler.feature_node.presentation.ruler

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.methods.TimeMethods
import java.util.*

@Composable
fun BottomDrawerItem(
    viewModel: RulerViewModel = hiltViewModel(),
    measurement: Measurement,
    unread: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                Text(
                    text = TimeMethods.getTimeString(LocalContext.current, measurement.timeStamp)
                )
            }
        }
    }
}
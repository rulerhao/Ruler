package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.feature_node.presentation.ruler.uicalculator.Calculation

@Preview
@Composable
fun GestureScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val lengthScale = viewModel.lengthScale

    val ACTION_IDLE = 0
    val ACTION_DOWN = 1
    val ACTION_MOVE = 2
    val ACTION_UP = 3

    val linePath = mutableListOf(
        Path(),
        Path()
    )
    val lineStartPosition = mutableListOf(
        Offset.Zero,
        Offset.Zero
    )
    val lineEndPosition = mutableListOf(
        Offset.Zero,
        Offset.Zero
    )

    var lineIndex = 0

    val path = remember { Path() }
    var motionEvent by remember { mutableStateOf(ACTION_IDLE) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    val drawModifier = Modifier
        .fillMaxSize()
        .clipToBounds()
        .pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {
                    awaitFirstDown().also {
                        motionEvent = ACTION_DOWN
                        currentPosition = it.position
                        lineStartPosition[lineIndex] = it.position
                    }

                    do {
                        val event: PointerEvent = awaitPointerEvent()
                        motionEvent = ACTION_MOVE
                        currentPosition = event.changes.first().position
                        lineEndPosition[lineIndex] = event.changes.first().position
                    } while (event.changes.any { it.pressed })

                    lineIndex =
                        if (lineIndex == 0) 1
                        else 0
                    motionEvent = ACTION_UP
                }
            }
        }

    Canvas(modifier = drawModifier) {
        when (motionEvent) {
            ACTION_DOWN -> {
                path.moveTo(currentPosition.x, currentPosition.y)
            }
            ACTION_MOVE -> {
                path.lineTo(currentPosition.x, currentPosition.y)
            }
            ACTION_UP -> {
                when (lineIndex) {
                    0 -> {
                        1.let {
                            linePath[it].moveTo(lineStartPosition[it].x, lineStartPosition[it].y)
                            linePath[it].lineTo(lineEndPosition[it].x, lineEndPosition[it].y)
                        }
                    }
                    1 -> {
                        linePath[0].reset()
                        linePath[1].reset()
                        0.let {
                            linePath[it].moveTo(lineStartPosition[it].x, lineStartPosition[it].y)
                            linePath[it].lineTo(lineEndPosition[it].x, lineEndPosition[it].y)
                        }
                        val verticalLines = Calculation.getVerticalUnitPosition(lineStartPosition[0], lineEndPosition[0], lengthScale.value.scale, context)
                        for (i in verticalLines.indices) {
                            drawLine(
                                color = Color.Blue,
                                start = verticalLines[i][0],
                                end = verticalLines[i][1],
                                strokeWidth = 5f
                            )
                        }
                    }
                }
                path.reset()
            }
            else -> Unit
        }
        drawPath(
            color = Color.Red,
            path = path,
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
        drawPath(
            color = Color.Green,
            path = linePath[0],
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
        drawPath(
            color = Color.Yellow,
            path = linePath[1],
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        drawLine(
            color = Color.Red,
            start = lineStartPosition[0],
            end = lineEndPosition[0],
            strokeWidth = 5f
        )
    }
}
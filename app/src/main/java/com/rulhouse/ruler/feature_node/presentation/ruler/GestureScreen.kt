package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun GestureScreen(

) {
    val ACTION_IDLE = 0
    val ACTION_DOWN = 1
    val ACTION_MOVE = 2
    val ACTION_UP = 3

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
                    }

                    do {
                        val event: PointerEvent = awaitPointerEvent()
                        motionEvent = ACTION_MOVE
                        currentPosition = event.changes.first().position
                    } while (event.changes.any { it.pressed })

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
                path.lineTo(currentPosition.x, currentPosition.y)
            }
            else -> Unit
        }

        drawPath(
            color = Color.Red,
            path = path,
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round)
        )
    }
}
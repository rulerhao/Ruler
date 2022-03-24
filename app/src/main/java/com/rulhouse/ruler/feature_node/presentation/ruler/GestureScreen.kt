package com.rulhouse.ruler.feature_node.presentation.ruler

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
//    var offset by remember { mutableStateOf(0f) }
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .scrollable(
//                orientation = Orientation.Vertical,
//                // Scrollable state: describes how to consume
//                // scrolling delta and update offset
//                state = rememberScrollableState { delta ->
//                    offset += delta
//                    delta
//                }
//            )
//            .background(Color.LightGray),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(offset.toString())
//    }


    val ACTION_IDLE = 0
    val ACTION_DOWN = 1
    val ACTION_MOVE = 2
    val ACTION_UP = 3

    val path = remember { Path() }
    var motionEvent by remember { mutableStateOf(ACTION_IDLE) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    var gestureColor by remember { mutableStateOf(Color.LightGray) }
    var gestureText by remember { mutableStateOf("Touch to Draw") }

    val drawModifier = Modifier
        .fillMaxSize()
        .background(gestureColor)
        .clipToBounds()
        .pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {
                    // Wait for at least one pointer to press down, and set first contact position
                    val down: PointerInputChange = awaitFirstDown().also {
                        motionEvent = ACTION_DOWN
                        currentPosition = it.position
                        gestureColor = Color.Blue
                    }

                    do {
                        // This PointerEvent contains details including events, id, position and more
                        val event: PointerEvent = awaitPointerEvent()

                        var eventChanges = "DOWN changedToDown: ${down.changedToDown()} changedUp: ${down.changedToUp()}\n"
                        event.changes
                            .forEachIndexed { index: Int, pointerInputChange: PointerInputChange ->
                                eventChanges += "Index: $index, id: ${pointerInputChange.id}, " +
                                        "changedUp: ${pointerInputChange.changedToUp()}" +
                                        "pos: ${pointerInputChange.position}\n"

                                // This necessary to prevent other gestures or scrolling
                                // when at least one pointer is down on canvas to draw
                                pointerInputChange.consumePositionChange()
                            }
                        gestureText = "EVENT changes size ${event.changes.size}\n" + eventChanges
                        gestureColor = Color.Green
                        motionEvent = ACTION_MOVE
                        currentPosition = event.changes.first().position
                    } while (event.changes.any { it.pressed })

                    motionEvent = ACTION_UP
                    gestureColor = Color.LightGray
                    gestureText += "UP changedToDown: ${down.changedToDown()} " +
                            "changedUp: ${down.changedToUp()}\n"
                }
            }
        }

    Canvas(modifier = drawModifier) {
        when (motionEvent) {
            ACTION_DOWN -> {
                path.moveTo(currentPosition.x, currentPosition.y)
            }
            ACTION_MOVE -> {
                if (currentPosition != Offset.Unspecified) {
                    path.lineTo(currentPosition.x, currentPosition.y)
                }
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
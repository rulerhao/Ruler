package com.rulhouse.ruler.feature_node.presentation.ruler.scale_screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerEvent
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScale
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerViewModel
import com.rulhouse.ruler.methods.ScreenMethods
import com.rulhouse.ruler.methods.ScaleTextGetter
import com.rulhouse.ruler.methods.ScaleTextPositionGetter
import com.rulhouse.ruler.ui.theme.*
import kotlin.math.abs


@Composable
fun ScaleScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    PreviewBlend(state.scale)
}

@Preview
@Composable
fun PreviewBlend(
    lengthScale: RulerScale = RulerScale.Centimeter,
    viewModel: ScaleScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.state.value

    /**
     * Scale area
     */
    val scaleAreaTopLeft = Offset(
        kotlin.math.min(
            state.left,
            state.right
        ).toFloat(),
        kotlin.math.min(
            state.top,
            state.bottom
        ).toFloat()
    )

    val scaleAreaSize =
        Size(
            width = abs(state.left - state.right).toFloat(),
            height = abs(state.top - state.bottom).toFloat()
        )

    /**
     * Scale area side text
     */
    val scaleAreaTextSize = 100f

    /**
     * Scale area top side text
     */
    val topTextString = ScaleTextGetter.textString(
        context = context,
        length = scaleAreaSize.width,
        scale = lengthScale
    )
    val topTextPaint = android.graphics.Paint().apply {
        textAlign = android.graphics.Paint.Align.RIGHT
        textSize = scaleAreaTextSize
        color = TextOnPColor.toArgb()
    }
    val topTextOffset = Offset(
        x = scaleAreaTopLeft.x + scaleAreaSize.width / 2,
        y = scaleAreaTopLeft.y
    )
    val topTextPosition = ScaleTextPositionGetter.topTextOffset(
        context = context,
        paint = topTextPaint,
        text = topTextString,
        offset = topTextOffset
    )

    /**
     * Scale area left side text
     */
    val leftTextString = ScaleTextGetter.textString(
        context = context,
        length = scaleAreaSize.height,
        scale = lengthScale
    )
    val leftTextPaint = android.graphics.Paint().apply {
        textAlign = android.graphics.Paint.Align.RIGHT
        textSize = scaleAreaTextSize
        color = TextOnPColor.toArgb()
    }
    val leftTextOffset = Offset(
        x = scaleAreaTopLeft.x,
        y = scaleAreaTopLeft.y + scaleAreaSize.height / 2
    )
    val leftTextPosition = ScaleTextPositionGetter.leftTextOffset(
        context = context,
        paint = topTextPaint,
        text = leftTextString,
        offset = leftTextOffset
    )

    /**
     * Scale line
     */
    // Draw Scale Line
    val scaleStrokeLength = 50f
    val scaleStrokeWidth = 5f
    val scaleLengthPerUnit = ScreenMethods.getScalePerUnit(context, lengthScale)

    /**
     * Scale text
     */
    val scaleTextSize = 60f
    // Scale width line text
    val scaleWidthLineTextPaint = android.graphics.Paint().apply {
        textAlign = android.graphics.Paint.Align.CENTER
        textSize = scaleTextSize
        color = TextOnPColor.toArgb()
    }
    // Scale height line text
    val scaleHeightLineTextPaint = android.graphics.Paint().apply {
        textAlign = android.graphics.Paint.Align.LEFT
        textSize = scaleTextSize
        color = TextOnPColor.toArgb()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        viewModel.onEvent(ScaleScreenEvent.OnDragStart(it))
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()

                        viewModel.onEvent(ScaleScreenEvent.OnDrag(dragAmount, context))
                    },
                    onDragEnd = {
                        viewModel.onEvent(ScaleScreenEvent.OnDragEnd)
                    }
                )
            }
    ) {
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            /**
             * Scale area
             */
            drawRect(
                color = PrimaryColor,
                topLeft = scaleAreaTopLeft,
                size = scaleAreaSize
            )

            /**
             * Scale area top side text
             */
            drawText(topTextString, topTextPosition.x, topTextPosition.y, topTextPaint)

            /**
             * Scale area left side text
             */
            drawText(leftTextString, leftTextPosition.x, leftTextPosition.y, leftTextPaint)

            // Draw Scale Line
            // width line
            for (i in 0..((size.width / scaleLengthPerUnit).toInt())) {
                val location = i * scaleLengthPerUnit
                if (i > 0) {
                    drawText(
                        i.toString(),
                        location,
                        scaleStrokeLength - scaleWidthLineTextPaint.ascent(),
                        scaleWidthLineTextPaint
                    )
                }
                drawLine(
                    color = SecondaryColor,
                    start = Offset(location, 0f),
                    end = Offset(location, scaleStrokeLength),
                    strokeWidth = scaleStrokeWidth,
                    blendMode = BlendMode.Multiply
                )

                val halfLocation = i * scaleLengthPerUnit + scaleLengthPerUnit / 2
                drawLine(
                    color = SecondaryDarkColor,
                    start = Offset(halfLocation, 0f),
                    end = Offset(halfLocation, scaleStrokeLength / 2),
                    strokeWidth = scaleStrokeWidth / 2,
                    blendMode = BlendMode.Multiply
                )
            }

            for (i in 0..((size.width / (scaleLengthPerUnit / 10)).toInt())) {
                val location = i * scaleLengthPerUnit / 10
                drawLine(
                    color = SecondaryLightColor,
                    start = Offset(location, 0f),
                    end = Offset(location, scaleStrokeLength / 3),
                    strokeWidth = scaleStrokeWidth / 3,
                    blendMode = BlendMode.Multiply
                )
            }

            // height line
            for (i in 0..((size.height / scaleLengthPerUnit).toInt())) {
                val location = i * scaleLengthPerUnit
                // draw height text
                if (i > 0) {
                    drawText(
                        i.toString(),
                        scaleStrokeLength + 20f,
                        location - (scaleHeightLineTextPaint.ascent() + scaleHeightLineTextPaint.descent()) / 2,
                        scaleHeightLineTextPaint
                    )
                }
                drawLine(
                    color = SecondaryColor,
                    start = Offset(0f, location),
                    end = Offset(scaleStrokeLength, location),
                    strokeWidth = scaleStrokeWidth,
                    blendMode = BlendMode.Multiply
                )

                val halfLocation = i * scaleLengthPerUnit + scaleLengthPerUnit / 2
                drawLine(
                    color = SecondaryDarkColor,
                    start = Offset(0f, halfLocation),
                    end = Offset(scaleStrokeLength / 2, halfLocation),
                    strokeWidth = scaleStrokeWidth / 2,
                    blendMode = BlendMode.Multiply
                )
            }

            for (i in 0..((size.height / (scaleLengthPerUnit / 10)).toInt())) {
                val location = i * scaleLengthPerUnit / 10
                drawLine(
                    color = SecondaryLightColor,
                    start = Offset(0f, location),
                    end = Offset(scaleStrokeLength / 3, location),
                    strokeWidth = scaleStrokeWidth / 3,
                    blendMode = BlendMode.Multiply
                )
            }

            restoreToCount(checkPoint)
        }
    }
}
package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.animation.core.*
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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.methods.ScaleTextGetter
import com.rulhouse.ruler.methods.ScaleTextPositionGetter
import com.rulhouse.ruler.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs


@Composable
fun ScaleScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val lengthScale = viewModel.state

    val animateFloat = remember {
        Animatable(
            0f
        )
    }

    LaunchedEffect(animateFloat) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RulerEvent.StartChangeScaleAnimation -> {
                    animateFloat.animateTo(
                        targetValue = 1f,
                        animationSpec = keyframes {
                            durationMillis = 500
                            0f at 0
                            1f at durationMillis
                        }
                    )
                    animateFloat.animateTo(
                        targetValue = 0f,
                        animationSpec = keyframes {
                            durationMillis = 500
                            1f at 0
                            0f at durationMillis
                        }
                    )
                }
                else -> {}
            }
        }
    }
    PreviewBlend(lengthScale.value.scale)
}

@Preview
@Composable
fun PreviewBlend(
    lengthScale: RulerScale = RulerScale.Centimeter,
    viewModel: RulerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.state.value

    var positionTouchDown = Offset.Unspecified
    var middlePositionWhenTouchDown = Offset.Unspecified

    /**
     * Scale area
     */
    var positionX1 by remember { mutableStateOf(200) }
    var positionX2 by remember { mutableStateOf(positionX1 + (state.scaleAreaSize.width * ScreenMethods.getPpc(context)).toInt()) }
    var positionY1 by remember { mutableStateOf(300) }
    var positionY2 by remember { mutableStateOf(positionY1 + (state.scaleAreaSize.height * ScreenMethods.getPpc(context)).toInt()) }
    val scaleAreaTopLeft = Offset(
        kotlin.math.min(positionX1, positionX2).toFloat(), kotlin.math.min(
            positionY1,
            positionY2
        ).toFloat()
    )
    val scaleAreaSize =
        Size(abs(positionX1 - positionX2).toFloat(), abs(positionY1 - positionY2).toFloat()).also {
            viewModel.onEvent(
                RulerEvent.ChangeScaleAreaSize(
                    com.rulhouse.ruler.feature_node.presentation.ruler.util.Size(
                        it.width / ScreenMethods.getPpc(context),
                        it.height / ScreenMethods.getPpc(context)
                    )
                )
            )
        }

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
    val topTextPaint = android.graphics.Paint()
    topTextPaint.textAlign = android.graphics.Paint.Align.RIGHT
    topTextPaint.textSize = scaleAreaTextSize
    topTextPaint.color = TextOnPColor.toArgb()
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
    val leftTextPaint = android.graphics.Paint()
    leftTextPaint.textAlign = android.graphics.Paint.Align.RIGHT
    leftTextPaint.textSize = scaleAreaTextSize
    leftTextPaint.color = TextOnPColor.toArgb()
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
    val scaleWidthLineTextPaint = android.graphics.Paint()
    scaleWidthLineTextPaint.textAlign = android.graphics.Paint.Align.CENTER
    scaleWidthLineTextPaint.textSize = scaleTextSize
    scaleWidthLineTextPaint.color = TextOnPColor.toArgb()
    // Scale height line text
    val scaleHeightLineTextPaint = android.graphics.Paint()
    scaleHeightLineTextPaint.textAlign = android.graphics.Paint.Align.LEFT
    scaleHeightLineTextPaint.textSize = scaleTextSize
    scaleHeightLineTextPaint.color = TextOnPColor.toArgb()
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        positionTouchDown = it
                        middlePositionWhenTouchDown = Offset(
                            ((positionX1 + positionX2) / 2).toFloat(),
                            ((positionY1 + positionY2) / 2).toFloat()
                        )
                    },
                    onDragEnd = {
                        if (positionX1 > positionX2) {
                            val temp = positionX2
                            positionX2 = positionX1
                            positionX1 = temp
                        }
                        if (positionY1 > positionY2) {
                            val temp = positionY2
                            positionY2 = positionY1
                            positionY1 = temp
                        }
                    }
                ) { change, dragAmount ->
                    change.consumeAllChanges()

                    if (positionTouchDown.x > middlePositionWhenTouchDown.x) {
                        positionX2 += dragAmount.x.toInt()
                        if (positionX2 > ScreenMethods.getWidth(context)) positionX2 =
                            ScreenMethods.getWidth(context)
                    } else {
                        positionX1 += dragAmount.x.toInt()
                        if (positionX1 < 0) positionX1 = 0
                    }
                    if (positionTouchDown.y > middlePositionWhenTouchDown.y) {
                        positionY2 += dragAmount.y.toInt()
                        if (positionY2 > ScreenMethods.getHeight(context)) positionY2 =
                            ScreenMethods.getHeight(context)
                    } else {
                        positionY1 += dragAmount.y.toInt()
                        if (positionY1 < 0) positionY1 = 0
                    }
                    viewModel.onEvent(
                        RulerEvent.ChangeScaleAreaSize(
                            com.rulhouse.ruler.feature_node.presentation.ruler.util.Size(
                                abs(positionX2 - positionX1) / ScreenMethods.getPpc(context),
                                abs(positionY2 - positionY1) / ScreenMethods.getPpc(context)
                            )
                        )
                    )
                }
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
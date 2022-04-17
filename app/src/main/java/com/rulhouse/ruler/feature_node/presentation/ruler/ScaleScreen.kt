package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
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
import com.rulhouse.ruler.methods.ScaleTextPositionGetter
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs


@Composable
fun ScaleScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
//    val context = LocalContext.current

    val lengthScale = viewModel.lengthScale

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

//    var positionX1 by remember { mutableStateOf(200) }
//    var positionX2 by remember { mutableStateOf(400) }
//    var positionY1 by remember { mutableStateOf(300) }
//    var positionY2 by remember { mutableStateOf(500) }
//
//    var positionTouchDown = Offset.Unspecified
//    var middlePositionWhenTouchDown = Offset.Unspecified

//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .clipToBounds()
//            .pointerInput(Unit) {
//                detectDragGestures(
//                    onDragStart = {
//                        positionTouchDown = it
//                        middlePositionWhenTouchDown = Offset(
//                            ((positionX1 + positionX2) / 2).toFloat(),
//                            ((positionY1 + positionY2) / 2).toFloat()
//                        )
//                    },
//                    onDragEnd = {
//                        if (positionX1 > positionX2) {
//                            val temp = positionX2
//                            positionX2 = positionX1
//                            positionX1 = temp
//                        }
//                        if (positionY1 > positionY2) {
//                            val temp = positionY2
//                            positionY2 = positionY1
//                            positionY1 = temp
//                        }
//                    }
//                ) { change, dragAmount ->
//                    change.consumeAllChanges()
//
//                    if (positionTouchDown.x > middlePositionWhenTouchDown.x) {
//                        positionX2 += dragAmount.x.toInt()
//                        if (positionX2 > ScreenMethods.getWidth(context)) positionX2 =
//                            ScreenMethods.getWidth(context)
//                    } else {
//                        positionX1 += dragAmount.x.toInt()
//                        if (positionX1 < 0) positionX1 = 0
//                    }
//                    if (positionTouchDown.y > middlePositionWhenTouchDown.y) {
//                        positionY2 += dragAmount.y.toInt()
//                        if (positionY2 > ScreenMethods.getHeight(context)) positionY2 =
//                            ScreenMethods.getHeight(context)
//                    } else {
//                        positionY1 += dragAmount.y.toInt()
//                        if (positionY1 < 0) positionY1 = 0
//                    }
//                }
//            }
//    ) {
//        drawIntoCanvas { canvas ->
//            val color1: Int = 0xffedb879.toInt()
//            val color3: Int = 0xffcce7e8.toInt()
//
//            val paint = Paint()
//
//            paint.color = color1
//            canvas.nativeCanvas.drawArc(
//                size.width * 1 / 2 - size.width * animateFloat.value,
//                size.height * 1 / 2 - size.height * animateFloat.value,
//                size.width * 1 / 2 + size.width * animateFloat.value,
//                size.height * 1 / 2 + size.height * animateFloat.value,
//                0F,
//                360F,
//                false,
//                paint
//            )
//
//            val testBitmap = Bitmap.createBitmap(
//                size.width.toInt(),
//                size.height.toInt(),
//                Bitmap.Config.ARGB_8888
//            )
//
//            val bitmapCanvas = Canvas(testBitmap)
//
//            paint.strokeWidth = 20f
//            paint.color = 0xff000000.toInt()
//
//            val ppi = ScreenMethods.getPpi(context)
//            val ppc = ppi / 2.54
//            when (lengthScale.value.scale) {
//                RulerScale.Centimeter -> {
//                    for (i in 0..((size.width / ppc).toInt())) {
//                        val location = i * ppc.toFloat()
//                        bitmapCanvas.drawLine(
//                            location,
//                            0f,
//                            location,
//                            100f,
//                            paint
//                        )
//                    }
//                }
//                RulerScale.Inch -> {
//                    for (i in 0..((size.width / ppi).toInt())) {
//                        val location = i * ppi
//                        bitmapCanvas.drawLine(
//                            location,
//                            0f,
//                            location,
//                            100f,
//                            paint
//                        )
//                    }
//                }
//            }
//
//            val overLapXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//            paint.xfermode = overLapXfermode
//            paint.color = color3
//            bitmapCanvas.drawArc(
//                size.width * 1 / 2 - size.width * animateFloat.value,
//                size.height * 1 / 2 - size.height * animateFloat.value,
//                size.width * 1 / 2 + size.width * animateFloat.value,
//                size.height * 1 / 2 + size.height * animateFloat.value,
//                0F,
//                360F,
//                false,
//                paint
//            )
//
//            paint.xfermode = null
//            canvas.nativeCanvas.drawBitmap(testBitmap, 0F, 0F, paint)
//
//            canvas.nativeCanvas.drawRect(
//                positionX1.toFloat(),
//                positionY1.toFloat(),
//                positionX2.toFloat(),
//                positionY2.toFloat(),
//                paint
//            )
//            paint.color = color1
//            paint.textSize = (abs(positionX1 - positionX2) / 10).toFloat()
//            paint.textAlign = Paint.Align.CENTER
//            Log.d("TestFont", "descent = ${paint.descent()}")
//            Log.d("TestFont", "ascent = ${paint.ascent()}")
//            paint.descent()
//            canvas.nativeCanvas.drawText(
//                "Test",
//                ((positionX1 + positionX2) / 2).toFloat(),
//                (positionY1 + positionY2) / 2 - (paint.descent() + paint.ascent()) / 2,
//                paint
//            )
//        }
//    }

//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .clipToBounds()
//            .pointerInput(Unit) {
//                detectDragGestures(
//                    onDragStart = {
//                        positionTouchDown = it
//                        middlePositionWhenTouchDown = Offset(
//                            ((positionX1 + positionX2) / 2).toFloat(),
//                            ((positionY1 + positionY2) / 2).toFloat()
//                        )
//                    },
//                    onDragEnd = {
//                        if (positionX1 > positionX2) {
//                            val temp = positionX2
//                            positionX2 = positionX1
//                            positionX1 = temp
//                        }
//                        if (positionY1 > positionY2) {
//                            val temp = positionY2
//                            positionY2 = positionY1
//                            positionY1 = temp
//                        }
//                    }
//                ) { change, dragAmount ->
//                    change.consumeAllChanges()
//
//                    if (positionTouchDown.x > middlePositionWhenTouchDown.x) {
//                        positionX2 += dragAmount.x.toInt()
//                        if (positionX2 > ScreenMethods.getWidth(context)) positionX2 =
//                            ScreenMethods.getWidth(context)
//                    } else {
//                        positionX1 += dragAmount.x.toInt()
//                        if (positionX1 < 0) positionX1 = 0
//                    }
//                    if (positionTouchDown.y > middlePositionWhenTouchDown.y) {
//                        positionY2 += dragAmount.y.toInt()
//                        if (positionY2 > ScreenMethods.getHeight(context)) positionY2 =
//                            ScreenMethods.getHeight(context)
//                    } else {
//                        positionY1 += dragAmount.y.toInt()
//                        if (positionY1 < 0) positionY1 = 0
//                    }
//                }
//            }
//    ) {
//        drawIntoCanvas { canvas ->
//            val colorBrown: Int = 0xffedb879.toInt()
//            val colorBlue: Int = 0xffcce7e8.toInt()
//
//            val scaleLinePaint = androidx.compose.ui.graphics.Paint()
//            scaleLinePaint.strokeWidth = 10f
//            scaleLinePaint.color = Color(colorBrown)
////            scaleLinePaint.blendMode = BlendMode.SrcIn
//
//            val scaleAreaPaint = androidx.compose.ui.graphics.Paint()
//            scaleAreaPaint.color = Color(colorBlue)
//            scaleAreaPaint.blendMode = BlendMode.SrcIn
//            canvas.drawRect(
//                positionX1.toFloat(),
//                positionY1.toFloat(),
//                positionX2.toFloat(),
//                positionY2.toFloat(),
//                scaleAreaPaint
//            )
//        }
//    }

    PreviewBlend(lengthScale.value.scale)
}

@Preview
@Composable
fun PreviewBlend(
    lengthScale: RulerScale = RulerScale.Centimeter
) {
    val context = LocalContext.current

    var positionTouchDown = Offset.Unspecified
    var middlePositionWhenTouchDown = Offset.Unspecified

    /**
     * Scale area
     */
    var positionX1 by remember { mutableStateOf(200) }
    var positionX2 by remember { mutableStateOf(400) }
    var positionY1 by remember { mutableStateOf(300) }
    var positionY2 by remember { mutableStateOf(500) }
    val scaleAreaTopLeft = Offset(
        kotlin.math.min(positionX1, positionX2).toFloat(), kotlin.math.min(
            positionY1,
            positionY2
        ).toFloat()
    )
    val scaleAreaSize =
        Size(abs(positionX1 - positionX2).toFloat(), abs(positionY1 - positionY2).toFloat())

    /**
     * Scale area side text
     */
    val scaleAreaTextSize = 100f

    /**
     * Scale area top side text
     */
    val topTextPaint = android.graphics.Paint()
    topTextPaint.textAlign = android.graphics.Paint.Align.RIGHT
    topTextPaint.textSize = scaleAreaTextSize
    val topTextOffset = Offset(
        x = scaleAreaTopLeft.x + abs(positionX2 - positionX1) / 2,
        y = scaleAreaTopLeft.y
    )
    val topTextPosition = ScaleTextPositionGetter.topTextOffset(
            context = context,
            paint = topTextPaint,
            text = "3CM",
            offset = topTextOffset
    )

    /**
     * Scale area left side text
     */
    val leftTextPaint = android.graphics.Paint()
    leftTextPaint.textAlign = android.graphics.Paint.Align.RIGHT
    leftTextPaint.textSize = scaleAreaTextSize
    val leftTextOffset = Offset(
        x = scaleAreaTopLeft.x,
        y = scaleAreaTopLeft.y + abs(positionY2 - positionY1) / 2
    )
    val leftTextPosition = ScaleTextPositionGetter.leftTextOffset(
        context = context,
        paint = topTextPaint,
        text = "3CM",
        offset = leftTextOffset
    )

    /**
     * Scale line
     */
    val scaleStrokeLength = 100f
    val scaleStrokeWidth = 10f
    val scaleLengthPerUnit = ScreenMethods.getScalePerUnit(context, lengthScale)
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
                }
            }
    ) {
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            /**
             * Scale area
             */
            drawRect(
                color = Color.Red,
                topLeft = scaleAreaTopLeft,
                size = scaleAreaSize,
            )

            /**
             * Scale area top side text
             */
            drawText("3cm", topTextPosition.x, topTextPosition.y, topTextPaint)

            /**
             * Scale area left side text
             */
            drawText("3cm", leftTextPosition.x, leftTextPosition.y, leftTextPaint)

            // Draw Scale Line
            // width line
            for (i in 0..((size.width / scaleLengthPerUnit).toInt())) {
                val location = i * scaleLengthPerUnit
                drawLine(
                    color = Color.Blue,
                    start = Offset(location, 0f),
                    end = Offset(location, scaleStrokeLength),
                    strokeWidth = scaleStrokeWidth,
                    blendMode = BlendMode.Multiply
                )
            }

            // height line
            for (i in 0..((size.height / scaleLengthPerUnit).toInt())) {
                val location = i * scaleLengthPerUnit
                drawLine(
                    color = Color.Blue,
                    start = Offset(0f, location),
                    end = Offset(scaleStrokeLength, location),
                    strokeWidth = scaleStrokeWidth,
                    blendMode = BlendMode.Multiply
                )
            }

            restoreToCount(checkPoint)
        }
    }
}
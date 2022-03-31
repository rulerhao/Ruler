package com.rulhouse.ruler.feature_node.presentation.ruler

import android.graphics.*
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import com.rulhouse.ruler.activity.ScreenMethods
import android.graphics.PorterDuff

import android.graphics.PorterDuffXfermode
import android.icu.number.Scale
import android.util.Log

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Screen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ScaleScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val lengthScale = viewModel.lengthScale

    val animateFloat = remember {
        Animatable(
            0f
        )
    }

    var positionX1 by remember {
        mutableStateOf(200)
    }
    var positionX2 by remember {
        mutableStateOf(400)
    }

    var positionY1 by remember {
        mutableStateOf(300)
    }
    var positionY2 by remember {
        mutableStateOf(500)
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
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                if (change.position.x > (positionX1 + positionX2) / 2) {
                    positionX2 += dragAmount.x.toInt()
                    if (positionX2 > ScreenMethods.getWidth(context)) positionX2 = ScreenMethods.getWidth(context)
                }
                else {
                    positionX1 += dragAmount.x.toInt()
                    if (positionX1 < 0) positionX1 = 0
                }
                if (change.position.y > (positionY1 + positionY2) / 2) {
                    positionY2 += dragAmount.y.toInt()
                    if (positionY2 > ScreenMethods.getHeight(context)) positionY2 = ScreenMethods.getHeight(context)
                }
                else {
                    positionY1 += dragAmount.y.toInt()
                    if (positionY1 < 0) positionY1 = 0
                }
            }
        }
    ) {
        drawIntoCanvas { canvas ->
            val color1: Int = 0xffedb879.toInt()
            val color3: Int = 0xffcce7e8.toInt()

            val paint = Paint()

            paint.color = color1
            canvas.nativeCanvas.drawArc(
                size.width * 1 / 2 - size.width * animateFloat.value,
                size.height * 1 / 2 - size.height * animateFloat.value,
                size.width * 1 / 2 + size.width * animateFloat.value,
                size.height * 1 / 2 + size.height * animateFloat.value,
                0F,
                360F,
                false,
                paint
            )
//
            val testBitmap = Bitmap.createBitmap(
                size.width.toInt(),
                size.height.toInt(),
                Bitmap.Config.ARGB_8888
            )

            val bitmapCanvas = Canvas(testBitmap)

            paint.strokeWidth = 20f
            paint.color = 0xff000000.toInt()

            val ppi = ScreenMethods.getPpi(context)
            val ppc = ppi / 2.54
            when (lengthScale.value.scale) {
                RulerScale.Centimeter -> {
                    for (i in 0..((size.width / ppc).toInt())) {
                        val location = i * ppc.toFloat()
                        bitmapCanvas.drawLine(
                            location,
                            0f,
                            location,
                            100f,
                            paint
                        )
                    }
                }
                RulerScale.Inch -> {
                    for (i in 0..((size.width / ppi).toInt())) {
                        val location = i * ppi
                        bitmapCanvas.drawLine(
                            location,
                            0f,
                            location,
                            100f,
                            paint
                        )
                    }
                }
            }

            val overLapXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            paint.xfermode = overLapXfermode
            paint.color = color3
            bitmapCanvas.drawArc(
                size.width * 1 / 2 - size.width * animateFloat.value,
                size.height * 1 / 2 - size.height * animateFloat.value,
                size.width * 1 / 2 + size.width * animateFloat.value,
                size.height * 1 / 2 + size.height * animateFloat.value,
                0F,
                360F,
                false,
                paint
            )

            paint.xfermode = null
            canvas.nativeCanvas.drawBitmap(testBitmap, 0F, 0F, paint)

            canvas.nativeCanvas.drawRect(
                positionX1.toFloat(),
                positionY1.toFloat(),
                positionX2.toFloat(),
                positionY2.toFloat(),
                paint
            )
        }
    }
}
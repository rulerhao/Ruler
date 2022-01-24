package com.rulhouse.ruler.feature_node.presentation.ruler

import android.R.attr.bitmap
import android.graphics.*
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import com.rulhouse.ruler.activity.ScreenMethods
import android.graphics.PorterDuff

import android.graphics.PorterDuffXfermode

import android.graphics.Xfermode
import androidx.compose.ui.graphics.nativeCanvas


@Composable
fun ScaleScreen (

) {
    val context = LocalContext.current
    Log.d("testDensity", "screenHeight = ${ScreenMethods.getHeight(context)}, screenWidth = ${ScreenMethods.getWidth(context)}")
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        Log.d("testDensity", "canvasWidth = $canvasWidth, canvasHeight = $canvasHeight")

        drawLine(
            start = Offset(x = ScreenMethods.getPpi(context), y = 0f),
            end = Offset(x = 0f, y = canvasHeight),
            color = Color.Blue
        )
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        drawIntoCanvas { canvas ->
            val color1 = 0xff1e81b0
            val color2 = 0xffeeeee4
            val color3 = 0xffe28743
            val color4 = 0xff21130d

            canvas.nativeCanvas.drawColor(color4.toInt());   // fill outside background

            val paint = Paint()
            paint.color = color1.toInt()
            canvas.nativeCanvas.drawArc(size.width * 0.36f, 20F, size.width * 3 / 4,
                size.height - 20, 0F, 360F, false, paint)

            val testBitmap = Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
            val bitmapCanvas = Canvas(testBitmap)
            paint.color = color2.toInt()
            paint.textSize = 80F
            bitmapCanvas.drawText("Hi I am some text", 50F, 140F, paint)

            val xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            paint.xfermode = xfermode
            paint.color = color3.toInt()
            bitmapCanvas.drawArc(size.width * 0.36f, 20F, size.width * 3 / 4,
                size.height - 20, 0F, 360F, false, paint)

            paint.xfermode = null
            canvas.nativeCanvas.drawBitmap(testBitmap,0F,0F,paint)

//            canvas.nativeCanvas.drawText("Hi I am some text", 150F, 140F, paint)
//            canvas.nativeCanvas.drawCircle(50f, 50f, 50f, paint)
        }
    }
}
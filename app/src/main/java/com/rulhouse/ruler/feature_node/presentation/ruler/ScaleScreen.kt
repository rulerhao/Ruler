package com.rulhouse.ruler.feature_node.presentation.ruler

import android.R.attr.bitmap
import android.graphics.*
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import com.rulhouse.ruler.activity.ScreenMethods
import android.graphics.PorterDuff

import android.graphics.PorterDuffXfermode

import android.graphics.Xfermode
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb


@Composable
fun ScaleScreen (

) {
    val context = LocalContext.current
    Log.d("testDensity", "screenHeight = ${ScreenMethods.getHeight(context)}, screenWidth = ${ScreenMethods.getWidth(context)}")
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        Log.d("testDensity", "canvasWidth = $canvasWidth, canvasHeight = $canvasHeight")

//        drawLine(
//            start = Offset(x = ScreenMethods.getPpi(context), y = 0f),
//            end = Offset(x = 0f, y = canvasHeight),
//            color = Color.Blue
//        )
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        drawIntoCanvas { canvas ->
            val color1: Int = 0xffedb879.toInt()
            val color2: Int = 0xff1979a9.toInt()
            val color3: Int = 0xffcce7e8.toInt()
            val color4: Int = 0xff44bcd8.toInt()

            val paint = Paint()

            canvas.nativeCanvas.drawLine(50f, 50f,50f * 2, 50f * 2, paint)
            canvas.nativeCanvas.drawColor(color4.toInt());   // fill outside background
            paint.color = color1.toInt()
            canvas.nativeCanvas.drawArc(size.width * 0.36f, 20F, size.width * 3 / 4,
                size.height - 20, 0F, 360F, false, paint)
//
            val testBitmap = Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
            val bitmapCanvas = Canvas(testBitmap)
            paint.color = color2.toInt()
            paint.textSize = 80F
            bitmapCanvas.drawText("Hi I am some text", 50F, 140F, paint)

            // inches
            paint.strokeWidth = 20f
            paint.color = 0xff000000.toInt()
            val ppi = ScreenMethods.getPpi(context)
//            for (i in 0..((size.width / ppi).toInt())) {
//                val location = i * ppi
//                bitmapCanvas.drawLine(i * ppi, 0f,i * ppi, 100f, paint)
//            }

            // centermieter
            val ppc = ppi / 2.54
            for (i in 0..((size.width / ppc).toInt())) {
                val location = i * ppc.toFloat()
                bitmapCanvas.drawLine(location, 0f,location, 100f, paint)
            }

            val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
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
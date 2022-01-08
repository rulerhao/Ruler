package com.rulhouse.ruler

import android.content.pm.ActivityInfo
import android.icu.util.MeasureUnit
import android.icu.util.MeasureUnit.CENTIMETER
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rulhouse.ruler.ui.theme.RulerTheme
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.material.Button
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalConfiguration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            DefaultPreview()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RulerTheme {
        val systemUiController = rememberSystemUiController()
//        val darkIcons = MaterialTheme.colors.isLight
//            if (systemUiController.isSystemBarsVisible) {
//            systemUiController.isSystemBarsVisible = false
//        }
//        SideEffect {
//            systemUiController.isSystemBarsVisible = false
//            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
//        }
//        val systemUiController = rememberSystemUiController()
//
//        systemUiController.isSystemBarsVisible = false // Status & Navigation bars

        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        Log.d("DisplayMetrics", "height = $screenHeight");
        Log.d("DisplayMetrics", "width = $screenWidth");
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val unit = CENTIMETER
//            dm.
//            getWindowManager().getDefaultDisplay().getMetrics(dm)
//            val x = Math.pow(mWidthPixels / dm.xdpi, 2.0)
//            val y = Math.pow(mHeightPixels / dm.ydpi, 2.0)
//            val screenInches = Math.sqrt(x + y)
//            Log.d("debug", "Screen inches : $screenInches")
            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0.dp.toPx(), y = canvasHeight),
                color = Color.Blue
            )
        }
        Button(
            onClick = {
                systemUiController.isSystemBarsVisible = !systemUiController.isSystemBarsVisible
            }
        ) {

        }
    }
}
package com.rulhouse.ruler

import android.content.pm.ActivityInfo
import android.graphics.Insets
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.rulhouse.ruler.ui.theme.RulerTheme
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScreen
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import android.os.Build
import android.util.Size
import android.view.View
import android.view.WindowInsets

import kotlinx.coroutines.*
import android.util.DisplayMetrics
import com.rulhouse.ruler.activity.ScreenMethods
import kotlin.math.pow
import kotlin.math.sqrt


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()

        hindSystemBar()

        addSystemUIListener()

        setContent {
            RulerTheme() {
                Surface(

                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RulerScreen.route
                    ) {
                        composable(
                            route = Screen.RulerScreen.route
                        ) {
                            RulerScreen()
                        }
                    }
                }
            }
        }
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val width = windowManager.currentWindowMetrics.bounds.width()
            val height = windowManager.currentWindowMetrics.bounds.height()
        }
        else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
        }
        val screenDensity = resources.displayMetrics.density
        val dotsPerInch = resources.displayMetrics.densityDpi
        Log.d("testDensity", "screenDensity = $screenDensity, dotsPerInch = $dotsPerInch")

        Log.d("testDensity", "real dpi = ${ScreenMethods.getPpi(this)}")
    }

    override fun onResume() {
        super.onResume()

        hideSystemBar()
    }

    private fun setOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun setSystemBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        hideSystemBar()
    }

    private fun hideSystemBar() {
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.systemBars())
    }

    fun getDensity(): Float {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val config = application.resources.configuration
            config.densityDpi / 160f
        } else {
            val metrics = application.resources.displayMetrics
            metrics.density
        }
    }

    private fun setShowSystemUi(show: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, show)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        if (show) {
            controller.show(WindowInsetsCompat.Type.systemBars())
        } else {
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun addSystemUIListener () {
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0)
                hindSystemBar()
        }
    }

    private fun hindSystemBar () = runBlocking {
        launch {
            delay(1000L)
            setShowSystemUi(false)
        }
    }
}
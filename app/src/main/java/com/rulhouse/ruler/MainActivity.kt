package com.rulhouse.ruler

import android.content.pm.ActivityInfo
import android.graphics.Insets
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
import android.view.WindowInsets

import android.view.WindowMetrics










@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSystemBar()
//
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            // Gets all excluding insets
            // Gets all excluding insets
            val metrics = windowManager.currentWindowMetrics
            val windowInsets = metrics.windowInsets
//            val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
//                WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars()
//            )
            val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.systemBars()
            )
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom
            Log.d("testDensity", "insetsWidth = $insetsWidth, insetsHeight = $insetsHeight")
            // Legacy size that Display#getSize reports

            // Legacy size that Display#getSize reports
            val bounds: Rect = metrics.bounds
            val legacySize = Size(
                bounds.width() - insetsWidth,
                bounds.height() - insetsHeight
            )
            Log.d("testDensity", "width = ${legacySize.width}, height = ${legacySize.height}")
            Log.d("testDensity", "bounds.width() = ${bounds.width()}, bounds.height() = ${bounds.height()}")
        } else {
        }

        var screenDensity = resources.displayMetrics.density
        var dotsPerInches = resources.displayMetrics.densityDpi
        var widthPixels = resources.displayMetrics.widthPixels
        var heightPixels = resources.displayMetrics.heightPixels
        Log.d("testDensity", "Dots per inches = $dotsPerInches, Density = $screenDensity, width = $widthPixels. height = $heightPixels")

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
}
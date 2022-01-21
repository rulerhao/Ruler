package com.rulhouse.ruler

import android.content.pm.ActivityInfo
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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var screenDensity = resources.displayMetrics.density
        var width = resources.displayMetrics.widthPixels
        var height = resources.displayMetrics.heightPixels
        Log.d("testDensity", "Density = $screenDensity, width = $width. height = $height")

//        setOrientation()
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
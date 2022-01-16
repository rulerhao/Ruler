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
import com.rulhouse.ruler.ui.theme.RulerTheme
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.material.Button
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScreen
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

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
                            RulerScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
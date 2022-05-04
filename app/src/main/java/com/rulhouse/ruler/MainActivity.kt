package com.rulhouse.ruler

import android.content.Context
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
import android.view.View

import kotlinx.coroutines.*
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.rulhouse.ruler.methods.ActivitySetting
import com.rulhouse.ruler.methods.ScreenMethods


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySetting.setOrientation(this)

        ActivitySetting.hindSystemBar(this)

        ActivitySetting.addSystemUIListener(this)

        setContent {
            RulerTheme {
                Surface {
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        super.onResume()

        ActivitySetting.hideSystemBar(this)
    }
}
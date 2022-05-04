package com.rulhouse.ruler.methods

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object ActivitySetting {
    fun setOrientation(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    fun setSystemBar(activity: Activity) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        WindowInsetsControllerCompat(activity.window, activity.window.decorView).systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        hideSystemBar(activity)
    }

    fun hideSystemBar(activity: Activity) {
        WindowInsetsControllerCompat(activity.window, activity.window.decorView).hide(WindowInsetsCompat.Type.systemBars())
    }

    fun setShowSystemUi(show: Boolean, activity: Activity) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, show)
        val controller = WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        if (show) {
            controller.show(WindowInsetsCompat.Type.systemBars())
        } else {
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun addSystemUIListener (activity: Activity) {
        activity.window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0)
                hindSystemBar(activity)
        }
    }

    fun hindSystemBar (activity: Activity) = runBlocking {
        launch {
            delay(1000L)
            setShowSystemUi(false, activity)
        }
    }
}
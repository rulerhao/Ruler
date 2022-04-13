package com.rulhouse.ruler.activity

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScale
import kotlin.math.pow
import kotlin.math.sqrt

class ScreenMethods {
    companion object {
        /**
         * Covert dp to px
         * @param dp
         * @param context
         * @return pixel
         */
        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * getDensity(context)
        }

        /**
         * Covert px to dp
         * @param px
         * @param context
         * @return dp
         */
        fun convertPixelToDp(px: Float, context: Context): Float {
            return px / getDensity(context)
        }

        /**
         * 取得螢幕密度
         * 120dpi = 0.75
         * 160dpi = 1 (default)
         * 240dpi = 1.5
         * @param context
         * @return
         */
        fun getDensity(context: Context): Float {
            return context.resources.displayMetrics.density
        }

        fun getDpi(context: Context): Int {
            return context.resources.displayMetrics.densityDpi
        }

        fun getPpi(context: Context): Float {
            val dm = context.resources.displayMetrics
            return dm.xdpi
        }

        fun getPpc(context: Context): Float {
            val dm = context.resources.displayMetrics
            return dm.xdpi / 2.54f
        }

        fun getScalePerUnit(context: Context, scale: RulerScale): Float {
            return when (scale) {
                RulerScale.Centimeter -> getPpc(context)
                RulerScale.Inch -> getPpi(context)
            }
        }

        fun getWidth(context: Context): Int {
            val activity = context as Activity
            @Suppress("DEPRECATION")
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity.windowManager.currentWindowMetrics.bounds.width()
            } else {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }

        fun getHeight(context: Context): Int {
            val activity = context as Activity
            @Suppress("DEPRECATION")
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity.windowManager.currentWindowMetrics.bounds.height()
            } else {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.heightPixels
            }
        }
    }
}
package com.rulhouse.ruler.methods

import android.content.Context
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScale

object ScaleTextGetter {
    fun textString(context: Context, length: Float, scale: RulerScale): String {
        when(scale) {
            RulerScale.Centimeter -> {
                return "%.2f".format(length / ScreenMethods.getPpc(context)) + " cm"
            }
            RulerScale.Inch -> {
                return "%.2f".format(length / ScreenMethods.getPpi(context)) + " in"
            }
        }
    }
}
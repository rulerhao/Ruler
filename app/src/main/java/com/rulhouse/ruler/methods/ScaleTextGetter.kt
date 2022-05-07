package com.rulhouse.ruler.methods

import android.content.Context
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScale

object ScaleTextGetter {
    fun textString(context: Context, length: Float, scale: RulerScale): String {
        return when(scale) {
            RulerScale.Centimeter -> {
                "%.2f".format(length / ScreenMethods.getPpc(context)) + " cm"
            }
            RulerScale.Inch -> {
                "%.2f".format(length / ScreenMethods.getPpi(context)) + " in"
            }
        }
    }
}
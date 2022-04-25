package com.rulhouse.ruler.feature_node.shared_pref

import android.content.Context
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScale
import com.rulhouse.ruler.feature_node.presentation.ruler.util.Size

object SharedPref {
    fun setLengthScale(scale: RulerScale, context: Context) {
        val pref = context.getSharedPreferences("Ruler", Context.MODE_PRIVATE)
        pref.edit().putInt("LengthScale", scale.ordinal).apply()
    }

    fun getLengthScale(context: Context): RulerScale {
        val pref = context.getSharedPreferences("Ruler", Context.MODE_PRIVATE)
        return RulerScale.values()[pref.getInt("LengthScale", RulerScale.Centimeter.ordinal)]
    }

    fun setScaleAreaSize(size: Size, context: Context) {
        val pref = context.getSharedPreferences("Ruler", Context.MODE_PRIVATE)
        pref.edit()
            .putFloat("ScaleAreaWidth", size.width)
            .putFloat("ScaleAreaHeight", size.height)
            .apply()
    }

    fun getScaleAreaSize(context: Context): Size {
        val pref = context.getSharedPreferences("Ruler", Context.MODE_PRIVATE)
        val width = pref.getFloat("ScaleAreaWidth", 200f)
        val height = pref.getFloat("ScaleAreaHeight", 200f)
        return Size(width = width, height = height)
    }
}
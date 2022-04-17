package com.rulhouse.ruler.methods

import android.graphics.Paint

object PaintTextSizeGetter {
    fun getTop(paint: Paint): Float {
        return paint.ascent()
    }

    fun getLeft(paint: Paint, text: String): Float {
        @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
        return when (paint.textAlign) {
            Paint.Align.RIGHT -> {
                - paint.measureText(text)
            }
            Paint.Align.LEFT -> {
                0f
            }
            Paint.Align.CENTER -> {
                - paint.measureText(text) / 2
            }
        }
    }
}
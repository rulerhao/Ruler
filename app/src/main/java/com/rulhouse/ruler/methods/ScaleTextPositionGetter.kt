package com.rulhouse.ruler.methods

import android.content.Context
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import com.rulhouse.ruler.activity.ScreenMethods

object ScaleTextPositionGetter {
    fun topTextOffset(context: Context, paint: Paint, text: String, offset: Offset): Offset {
        return Offset(
            topTextX(context, paint, text, offset),
            topTextY(context, paint, offset)
        )
    }

    private fun topTextX(context: Context, paint: Paint, text: String, offset: Offset): Float {
        val textMiddleX = offset.x + paint.measureText(text) / 2

        return if (textMiddleX - paint.measureText(text) < 0) paint.measureText(text)
            else if (textMiddleX > ScreenMethods.getWidth(context = context)) ScreenMethods.getWidth(context = context).toFloat()
            else textMiddleX
    }

    private fun topTextY(context: Context, paint: Paint, offset: Offset): Float {
        val textY = offset.y

        return if (textY < - paint.ascent()) - paint.ascent()
        else if (textY > ScreenMethods.getHeight(context = context)) ScreenMethods.getHeight(context = context).toFloat()
        else textY
    }

    fun leftTextOffset(context: Context, paint: Paint, text: String, offset: Offset): Offset {
        return Offset(
            leftTextX(context, paint, text, offset),
            leftTextY(context, paint, offset)
        )
    }

    private fun leftTextX(context: Context, paint: Paint, text: String, offset: Offset): Float {
        val textX = offset.x

        return if (textX - paint.measureText(text) < 0) paint.measureText(text)
        else if (textX > ScreenMethods.getWidth(context = context)) ScreenMethods.getWidth(context = context).toFloat()
        else textX
    }

    private fun leftTextY(context: Context, paint: Paint, offset: Offset): Float {
        val textY = offset.y - (paint.descent() + paint.ascent()) / 2

        return if (textY < - paint.ascent()) - paint.ascent()
        else if (textY > ScreenMethods.getHeight(context = context)) ScreenMethods.getHeight(context = context).toFloat()
        else textY
    }
}
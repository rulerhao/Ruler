package com.rulhouse.ruler.methods

import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.compose.ui.platform.LocalContext
import java.util.*

object TimeMethods {
    fun getDateString(context: Context, timeStamp: Long): String {
        val locale = context.resources.configuration.locales.get(0)
        val dateSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        return dateSimpleDateFormat.format(Date(timeStamp))
    }

    fun getTimeString(context: Context, timeStamp: Long): String {
        val locale = context.resources.configuration.locales.get(0)
        val timeSimpleDateFormat = SimpleDateFormat("HH:mm:ss", locale)

        return timeSimpleDateFormat.format(Date(timeStamp))
    }
}
package com.rulhouse.ruler.activity

object LengthScaleChanger {

    fun cmToIn(cm: Float): Float {
        return cm / 2.54f
    }
    fun cmToIn(cm: Int): Float {
        return cm / 2.54f
    }

    fun inToCm(inches: Float): Float  {
        return inches * 2.54f
    }
    fun inToCm(inches: Int): Float  {
        return inches * 2.54f
    }
}
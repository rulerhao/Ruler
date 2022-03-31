package com.rulhouse.ruler.feature_node.presentation.ruler.uicalculator

import android.content.Context
import androidx.compose.ui.geometry.Offset
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerScale
import kotlin.math.pow
import kotlin.math.sqrt

class Calculation {
    companion object {
        fun getVerticalPosition(
            startPosition: Offset,
            endPosition: Offset,
            rulerScale: RulerScale,
            context: Context
        ): List<List<Offset>> {
            val ans: MutableList<List<Offset>> = ArrayList()

            val diffX = (startPosition.x - endPosition.x)
            val diffY = (startPosition.y - endPosition.y)
            val diffM = diffY / diffX
            val diff = sqrt(diffX.pow(2) + diffY.pow(2))

            val ppi = ScreenMethods.getPpi(context)
            val ppc = ppi / 2.54

            val verticalLength = 50f
            val verticalM = -1 / diffM
            val verticalLengthX = sqrt(verticalLength.pow(2) / (verticalM.pow(2) + 1))
            val verticalLengthY = verticalLengthX * verticalM
            when (rulerScale) {
                RulerScale.Centimeter -> {
                    val unitLengthX = sqrt(ppc.pow(2) / (diffM.pow(2) + 1))
                    val unitLengthY = unitLengthX * diffM
                    val unitLengthXWithSign =
                        if (diffX > 0) unitLengthX
                        else -unitLengthX
                    val unitLengthYWithSign =
                        if (diffX > 0) unitLengthY
                        else -unitLengthY
                    for (i in 0..((diff / ppc).toInt())) {
                        ans.add(
                            listOf(
                                Offset(
                                    (startPosition.x - unitLengthXWithSign * i + verticalLengthX).toFloat(),
                                    (startPosition.y - unitLengthYWithSign * i + verticalLengthY).toFloat()
                                ),
                                Offset(
                                    (startPosition.x - unitLengthXWithSign * i - verticalLengthX).toFloat(),
                                    (startPosition.y - unitLengthYWithSign * i - verticalLengthY).toFloat()
                                )
                            )
                        )
                    }
                }
                RulerScale.Inch -> {
                    val unitLengthX = sqrt(ppi.pow(2) / (diffM.pow(2) + 1))
                    val unitLengthY = unitLengthX * diffM
                    val unitLengthXWithSign =
                        if (diffX > 0) unitLengthX
                        else -unitLengthX
                    val unitLengthYWithSign =
                        if (diffX > 0) unitLengthY
                        else -unitLengthY
                    for (i in 0..((diff / ppi).toInt()))
                        ans.add(
                            listOf(
                                Offset(
                                    (startPosition.x - unitLengthXWithSign * i + verticalLengthX),
                                    (startPosition.y - unitLengthYWithSign * i + verticalLengthY)
                                ),
                                Offset(
                                    (startPosition.x - unitLengthXWithSign * i - verticalLengthX),
                                    (startPosition.y - unitLengthYWithSign * i - verticalLengthY)
                                )
                            )
                        )
                }
            }
            return ans
        }
    }
}
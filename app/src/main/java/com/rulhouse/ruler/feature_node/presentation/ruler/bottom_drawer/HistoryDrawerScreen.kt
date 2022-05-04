package com.rulhouse.ruler.feature_node.presentation.ruler.bottom_drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.methods.ScreenMethods
import com.rulhouse.ruler.feature_node.presentation.ruler.BottomDrawerItem
import com.rulhouse.ruler.feature_node.presentation.ruler.RulerViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryDrawerScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    val rangeToTop = ScreenMethods.convertPixelToDp(
        ScreenMethods.getHeight(context = context).toFloat(),
        context = context
    ) / 4
    Box(
        modifier = Modifier
            .padding(top = rangeToTop.dp)
            .background(color = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                )
//                .padding(8.dp),
        ) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(state.measurements) { measurement ->
                    BottomDrawerItem(
                        measurement = measurement
                    )
                }
            }
        }
    }
}
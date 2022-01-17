package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RulerScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val rulerState = viewModel.isSystemBarShow.value

    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = rulerState.isSystemBarVisible
    Button(
        modifier = Modifier
            .padding(20.dp),
        onClick = {
            viewModel.onEvent(RulerEvent.ToggleSystemBar)
        }
    ) {
        Text(text = rulerState.toString())
    }
}

package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RulerScreen(
    navController: NavController,
    viewModel: RulerViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()

    Button(
        onClick = {
            systemUiController.isSystemBarsVisible = !systemUiController.isSystemBarsVisible
        }
    ) {

    }
}

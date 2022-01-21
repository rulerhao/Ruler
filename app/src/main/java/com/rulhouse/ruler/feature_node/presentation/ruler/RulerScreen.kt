package com.rulhouse.ruler.feature_node.presentation.ruler

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rulhouse.ruler.R
import com.rulhouse.ruler.ui.theme.RulerTheme

@Composable
fun RulerScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val rulerState = viewModel.isSystemBarShow.value
    Box {
        Image(
            painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "...",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
        ) {
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
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        Log.d("testDensity", "canvasWidth = $canvasWidth, canvasHeight = $canvasHeight")

        drawLine(
            start = Offset(x = canvasWidth, y = 0f),
            end = Offset(x = 0f, y = canvasHeight),
            color = Color.Blue
        )
    }
}

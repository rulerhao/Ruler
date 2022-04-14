package com.rulhouse.ruler.feature_node.presentation.ruler

import android.graphics.Point
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rulhouse.ruler.R
import com.rulhouse.ruler.activity.ScreenMethods
import com.rulhouse.ruler.ui.theme.RulerTheme
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RulerScreen(
//    viewModel: RulerViewModel = hiltViewModel()
) {
    BottomDrawer(
        drawerContent = {
            HistoryDrawerScreen()
        },
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp
    ) {
//        Box {
//            ScaleScreen()
////        GestureScreen()
//            Column(
//            ) {
//                Button(
//                    modifier = Modifier
//                        .padding(20.dp),
//                    onClick = {
//                        viewModel.onEvent(RulerEvent.SetScale(RulerScale.Inch))
//                    }
//                ) {
//                    Text(text = "toInch")
//                }
//                Button(
//                    modifier = Modifier
//                        .padding(20.dp),
//                    onClick = {
//                        viewModel.onEvent(RulerEvent.SetScale(RulerScale.Centimeter))
//                    }
//                ) {
//                    Text(text = "toCM")
//                }
//                Button(
//                    modifier = Modifier
//                        .padding(20.dp),
//                    onClick = {
//                        viewModel.onEvent(RulerEvent.SaveMeasurement)
//                    }
//                ) {
//                    Text(text = "Save Test")
//                }
//            }
//        }
    }
}

package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.rounded.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rulhouse.ruler.feature_node.presentation.ruler.bottom_drawer.HistoryDrawerScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Composable
fun RulerScreen(
    viewModel: RulerViewModel = hiltViewModel()
) {
    val scaleChangeButtonText = when (viewModel.state.value.scale) {
        RulerScale.Centimeter -> {
            "IN"
        }
        RulerScale.Inch -> {
            "CM"
        }
    }

    val snackbarState = remember { mutableStateOf(false) }
    val snackbarMessageState = remember { mutableStateOf("") }
    val snackbarActionMessageState = remember { mutableStateOf("") }
    val snackbarRestoreActionEventState = remember { mutableStateOf(RulerUiEvent.UiEvent.None) }
    var snackbarJob: Job? = null

    LaunchedEffect(
        key1 = true
    ) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is RulerUiEvent.ShowSnackbar -> {
                }
                is RulerUiEvent.DeleteMeasurement -> {
                    snackbarState.value = true
                    snackbarMessageState.value = "Measurement deleted"
                    snackbarActionMessageState.value = "Undo"
                    snackbarRestoreActionEventState.value = RulerUiEvent.UiEvent.DeleteMeasurement

                    snackbarJob?.cancel()
                    snackbarJob = GlobalScope.launch {
                        delay(2000L)
                        snackbarState.value = false
                    }
                }
                is RulerUiEvent.SaveMeasurement -> {
                    snackbarState.value = true
                    snackbarMessageState.value = "Saved measurement!"
                    snackbarRestoreActionEventState.value = RulerUiEvent.UiEvent.SaveMeasurement

                    snackbarJob?.cancel()
                    snackbarJob = GlobalScope.launch {
                        delay(1000L)
                        snackbarState.value = false
                    }
                }
            }
        }
    }

    BottomDrawer(
        drawerContent = {
            HistoryDrawerScreen()
        },
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp,
        drawerState = viewModel.drawerState.value
    ) {
        Box {
            ScaleScreen()
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        viewModel.onEvent(RulerEvent.SwitchScale)
                    },
                    shape = CircleShape
                ) {
                    Text(text = scaleChangeButtonText)
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        viewModel.onEvent(RulerEvent.ToggleSaveDrawer)
                    },
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Rounded.History,
                        contentDescription = "History",
                    )
                }
            }
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    viewModel.onEvent(
                        RulerEvent.SaveMeasurement(
                            title = "",
                            size = viewModel.state.value.scaleAreaSize
                        )
                    )
                },
                shape = CircleShape,
            ) {
                Icon(
                    Icons.Filled.Save,
                    contentDescription = "Save",
                )
            }
        }
    }
    if (snackbarState.value) {
        Snackbar(
            modifier = Modifier
                .padding(16.dp),
            action = {
                when (snackbarRestoreActionEventState.value) {
                    RulerUiEvent.UiEvent.DeleteMeasurement -> {
                        Button(
                            onClick = {
                                snackbarJob?.cancel()
                                snackbarState.value = false
                                viewModel.onEvent(RulerEvent.RestoreNote)
                            }
                        ) {
                            Text(text = snackbarActionMessageState.value)
                        }
                    }
                    else -> {}
                }
            }
        ) {
            Text(text = snackbarMessageState.value)
        }
    }
}
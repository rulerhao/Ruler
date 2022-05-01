package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rulhouse.ruler.feature_node.domain.model.Measurement
import com.rulhouse.ruler.ui.theme.SecondaryColor

@Composable
fun BottomDrawerEditDialog(
    measurement: Measurement,
    onDismissRequest: () -> Unit,
    onOkClick: (String) -> Unit
) {
    val titleState = remember {
        mutableStateOf(
            TransparentHintTextFieldObject(
                text = measurement.title,
                hint = "Please input the title",
                isHintVisible = true
            )
        )
    }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                ),
        ) {
            TransparentHintTextField(
                text = titleState.value.text,
                hint = titleState.value.hint,
                onValueChange = {
                    titleState.value = TransparentHintTextFieldObject(
                        text = it,
                        hint = titleState.value.hint,
                        isHintVisible = titleState.value.isHintVisible
                    )
                },
                onFocusChange = {
                    titleState.value = TransparentHintTextFieldObject(
                        text = titleState.value.text,
                        hint = titleState.value.hint,
                        isHintVisible = !it.isFocused &&
                                titleState.value.text.isBlank()
                    )
                },
                isHintVisible = titleState.value.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                    .background(SecondaryColor),
                onDone = {
                    onOkClick(titleState.value.text)
                    onDismissRequest()
                }
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = {
                        onOkClick(titleState.value.text)
                        onDismissRequest()
                    },
                ) {
                    Text(
                        text = "Edit"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )
                Button(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        }
    }
}
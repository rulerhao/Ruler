package com.rulhouse.ruler.feature_node.presentation.ruler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rulhouse.ruler.ui.theme.SecondaryColor
import com.rulhouse.ruler.ui.theme.SecondaryLightColor

@Composable
fun BottomDrawerEditDialog(
    onDismissRequest: () -> Unit
) {
    val contentState = remember { mutableStateOf(TransparentHintTextFieldObject(
        text = "",
        hint = "Please input the title",
        isHintVisible = true
    ))}

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Card(
        ) {

        }
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
                text = contentState.value.text,
                hint = contentState.value.hint,
                onValueChange = {
                    contentState.value = TransparentHintTextFieldObject(
                        text = it,
                        hint = contentState.value.hint,
                        isHintVisible = contentState.value.isHintVisible
                    )
                },
                onFocusChange = {
                    contentState.value = TransparentHintTextFieldObject(
                        text = contentState.value.text,
                        hint = contentState.value.hint,
                        isHintVisible = !it.isFocused &&
                                contentState.value.text.isBlank()
                    )
                },
                isHintVisible = contentState.value.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = {

                    },
                ) {
                    Text(
                        text = "OK"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )
                Button(
                    onClick = {

                    }
                ) {
                    Text(
                        text = "NO"
                    )
                }
            }
        }
    }
}
package com.example.quanlynhansu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.R

@Composable
fun ImageComponent(
    size: Dp = 50.dp,
    image: Int = R.drawable.ic_launcher_foreground
) {
    Image(
        modifier = Modifier.size(size),
        painter = painterResource(id = image),
        contentDescription = null
    )
}

@Preview
@Composable
private fun ImageComponentPreview() {
    ImageComponent(180.dp, R.drawable.logo)
}

@Composable
fun TextComponent(
    textValue: String = "",
    color: Color = Color.Black,
    textSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.W400,
    modifier: Modifier
) {
    Text(
        text = textValue,
        color = color,
        fontSize = textSize,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TextComponentPreview() {
    TextComponent(
        textValue = "ỨNG DỤNG QUẢN LÝ NHÂN SỰ",
        color = Color(0xffFD6229),
        textSize = 22.sp,
        fontWeight = FontWeight.W500,
        modifier = Modifier
    )
}

@Composable
fun ButtonComponent(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    borderWeight: Dp,
    borderColor: Color,
    shapeWeight: Dp,
    elevationWeight: Dp,
    modifier: Modifier,
    textValue: String,
    textSize: TextUnit,
    fontWeight: FontWeight
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(borderWeight, color = borderColor),
        shape = RoundedCornerShape(shapeWeight),
        elevation = ButtonDefaults.buttonElevation(elevationWeight),
        modifier = modifier
    ) {
        Text(
            text = textValue,
            fontSize = textSize,
            fontWeight = fontWeight
        )
    }
}

@Preview
@Composable
private fun ButtonComponentPreview() {
    ButtonComponent(
        onClick = {},
        containerColor = Color(0xffea9010),
        contentColor = Color.White,
        borderWeight = 1.dp,
        borderColor = Color.Transparent,
        shapeWeight = 6.dp,
        elevationWeight = 10.dp,
        modifier = Modifier.width(350.dp),
        textValue = "Đăng nhập",
        textSize = 16.sp,
        fontWeight = FontWeight.W500
    )
}

@Composable
fun TextFieldComponent(
    defaultValue: String,
    onTextChanged: (value: String) -> Unit,
    textStyle: TextStyle,
    placeholderValue: String,
    colors: TextFieldColors,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    visualTransformation: VisualTransformation,
    modifier: Modifier

) {
    var currentValue by remember {
        mutableStateOf(defaultValue)
    }

    TextField(
        value = currentValue,
        onValueChange = {
            currentValue = it
            onTextChanged(it)
        },
        textStyle = textStyle,
        placeholder = {
            Text(text = placeholderValue, fontSize = 14.sp)
        },
        colors = colors,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TextFieldComponentPreview() {
    TextFieldComponent(
        defaultValue = "",
        onTextChanged = {},
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W400
        ),
        placeholderValue = "Tên đăng nhập",
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color(0xffA26F38),
            unfocusedTextColor = Color(0xffA26F38),
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color(0xffA26F38),
            unfocusedLabelColor = Color(0xffA26F38),
            focusedLeadingIconColor = Color(0xffea9010),
            unfocusedLeadingIconColor = Color(0xffea9010)
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onNext = {
//                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = null)
        },
        trailingIcon = null,
        visualTransformation = VisualTransformation.None,
        modifier = Modifier.width(350.dp)
    )
}

@Composable
fun AlertDialogComponent(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Hủy")
            }
        }
    )
}

@Preview
@Composable
private fun AlertDialogComponentPreview() {
    AlertDialogComponent(
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "",
        dialogText = "",
        icon = Icons.Default.Warning
    )
}
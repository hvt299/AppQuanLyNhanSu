package com.example.quanlynhansu.ui

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.quanlynhansu.R
import com.example.quanlynhansu.models.PieChartData
import com.example.quanlynhansu.ui.theme.blueColor
import com.example.quanlynhansu.ui.theme.greenColor
import com.example.quanlynhansu.ui.theme.lightgrayColor
import com.example.quanlynhansu.ui.theme.redColor
import com.example.quanlynhansu.ui.theme.yellowColor
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

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

// on below line we are creating a
// pie chart function on below line.
@Composable
fun PieChart(title: String, getPieChartData: List<PieChartData>) {
    // on below line we are creating a column
    // and specifying a modifier as max size.
    Column(modifier = Modifier.fillMaxSize()) {
        // on below line we are again creating a column
        // with modifier and horizontal and vertical arrangement
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // on below line we are creating a simple text
            // and specifying a text as Web browser usage share
            Text(
                text = title,

                // on below line we are specifying style for our text
                style = TextStyle.Default,

                // on below line we are specifying font family.
//                fontFamily = FontFamily().Default,

                // on below line we are specifying font style
                fontStyle = FontStyle.Normal,

                // on below line we are specifying font size.
                fontSize = 16.sp,

                color = Color.Black,
                fontWeight = FontWeight.W300

            )

            // on below line we are creating a column and
            // specifying the horizontal and vertical arrangement
            // and specifying padding from all sides.
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(360.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are creating a cross fade and
                // specifying target state as pie chart data the
                // method we have created in Pie chart data class.
                Crossfade(targetState = getPieChartData) { pieChartData ->
                    // on below line we are creating an
                    // android view for pie chart.
                    AndroidView(factory = { context ->
                        // on below line we are creating a pie chart
                        // and specifying layout params.
                        com.github.mikephil.charting.charts.PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                // on below line we are specifying layout
                                // params as MATCH PARENT for height and width.
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            // on below line we are setting description
                            // enables for our pie chart.
                            this.description.isEnabled = false

                            // on below line we are setting draw hole
                            // to false not to draw hole in pie chart
                            this.isDrawHoleEnabled = true

                            // on below line we are enabling legend.
                            this.legend.isEnabled = true

                            // on below line we are specifying
                            // text size for our legend.
                            this.legend.textSize = 12F

                            // on below line we are specifying
                            // alignment for our legend.
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER

                            // on below line we are specifying entry label color as white.
                            this.setEntryLabelColor(resources.getColor(R.color.white))

                            this.animateY(1000, Easing.EaseInCubic)
                        }
                    },
                        // on below line we are specifying modifier
                        // for it and specifying padding to it.
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {
                            // on below line we are calling update pie chart
                            // method and passing pie chart and list of data.
                            updatePieChartWithData(it, pieChartData)
                        })
                }
            }
        }
    }
}

// on below line we are creating a update pie
// chart function to update data in pie chart.
fun updatePieChartWithData(
    // on below line we are creating a variable
    // for pie chart and data for our list of data.
    chart: PieChart,
    data: List<PieChartData>
) {
    // on below line we are creating
    // array list for the entries.
    val entries = ArrayList<PieEntry>()

    // on below line we are running for loop for
    // passing data from list into entries list.
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.dataName ?: ""))
    }

    // on below line we are creating
    // a variable for pie data set.
    val ds = PieDataSet(entries, "")

    // on below line we are specifying color
    // int the array list from colors.
    ds.colors = arrayListOf(
        yellowColor.toArgb(),
        greenColor.toArgb(),
        blueColor.toArgb(),
        redColor.toArgb(),
    )
    // on below line we are specifying position for value
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying position for value inside the slice.
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying
    // slice space between two slices.
    ds.sliceSpace = 2f

    // on below line we are specifying text color
    ds.valueTextColor = R.color.white

    // on below line we are specifying
    // text size for value.
    ds.valueTextSize = 12f

    // on below line we are specifying type face as bold.
    ds.valueTypeface = Typeface.DEFAULT_BOLD

    // on below line we are creating
    // a variable for pie data
    val d = PieData(ds)

    // on below line we are setting this
    // pie data in chart data.
    chart.data = d

    // on below line we are
    // calling invalidate in chart.
    chart.invalidate()
}
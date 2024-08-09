package com.example.quanlynhansu.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.firebase.getEmployeeBaseOnAge
import com.example.quanlynhansu.models.PieChartData
import com.example.quanlynhansu.ui.PieChart
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticalEmployeeScreen(
    backInfoScreen: () -> Unit
) {
    var employeeBaseOnAgeList by remember { mutableStateOf(listOf(0, 0, 0, 0)) }
    var totalEmployeeBaseOnAge by remember { mutableStateOf(1) }
    var total1 by remember { mutableStateOf(0) }
    var total2 by remember { mutableStateOf(0) }
    var total3 by remember { mutableStateOf(0) }
    var total4 by remember { mutableStateOf(0) }
    var getPieChartData by remember { mutableStateOf(emptyList<PieChartData>()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val employeeData = getEmployeeBaseOnAge()
            employeeBaseOnAgeList = employeeData
            totalEmployeeBaseOnAge = employeeData.sum()

            // Tránh chia cho 0 khi tổng số nhân viên là 0
            val safeTotal = if (totalEmployeeBaseOnAge == 0) 1 else totalEmployeeBaseOnAge
            total1 = (employeeBaseOnAgeList[0] * 100) / safeTotal
            total2 = (employeeBaseOnAgeList[1] * 100) / safeTotal
            total3 = (employeeBaseOnAgeList[2] * 100) / safeTotal
            total4 = (employeeBaseOnAgeList[3] * 100) / safeTotal

            // Cập nhật lại dữ liệu pie chart
            getPieChartData = listOf(
                PieChartData("18 - 25", total1.toFloat()),
                PieChartData("26 - 35", total2.toFloat()),
                PieChartData("36 - 45", total3.toFloat()),
                PieChartData("Trên 45", total4.toFloat())
            )

            // Logging để kiểm tra dữ liệu
            Log.d("DataDebug", "Employee Base On Age List: $employeeBaseOnAgeList")
            Log.d("DataDebug", "Total Employee Base On Age: $totalEmployeeBaseOnAge")
            Log.d("DataDebug", "Pie Chart Data: $getPieChartData")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFD6229),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Thông tin hồ sơ", fontSize = 18.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backInfoScreen()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "THỐNG KÊ NHÂN SỰ",
                    color = Color(0xffFD6229),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            PieChart("Nhân sự theo độ tuổi", getPieChartData)
        }
    }
}

@Preview
@Composable
private fun StatisticalEmployeeScreenPreview() {
    StatisticalEmployeeScreen(
        backInfoScreen = {}
    )
}
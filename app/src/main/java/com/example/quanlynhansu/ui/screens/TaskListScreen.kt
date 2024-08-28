package com.example.quanlynhansu.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.firebase.getEmployeeByUserID
import com.example.quanlynhansu.firebase.getTaskByEmployeeID
import com.example.quanlynhansu.models.Employee
import com.example.quanlynhansu.models.Task
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    userID: String,
    backTaskScreen: () -> Unit
) {
    val df1 = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//    val nf = NumberFormat.getInstance(Locale.US)

    var employee by remember {
        mutableStateOf(Employee())
    }

    var fullname by remember {
        mutableStateOf("")
    }

    var taskList by remember {
        mutableStateOf<List<Task>>(emptyList())
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            employee = getEmployeeByUserID(userID)
            fullname = employee.fullname
            taskList = getTaskByEmployeeID(employee.employeeID)
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
                    Text(text = "Việc được giao", fontSize = 18.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backTaskScreen()
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
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "DANH SÁCH CÔNG VIỆC ĐƯỢC GIAO",
                    color = Color(0xffFD6229),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    itemsIndexed(
                        items = taskList,
                        itemContent = { _, item ->
//                            AnimatedVisibility(
//                                visible = !deletedItem.contains(item),
//                                enter = expandVertically(),
//                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
//                            ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .wrapContentHeight()
                                    .aspectRatio(2.5f)
                                    .combinedClickable(
                                        onClick = {
//                                                showProfileScreen(item.userID)
                                        },
                                        onLongClick = {
//                                                deleteUserID = item.userID
//                                                deleteEmployeeID = item.employeeID
//                                                openDeleteAlertDialog = !openDeleteAlertDialog
//                                                if (checkingDelete) {
//                                                    deletedItem.add(item)
//                                                    checkingDelete = false
//                                                }
                                        }
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(
                                                onClick = {},
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    containerColor = Color.Transparent,
                                                    contentColor = Color(0xffea9010)
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.List,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(28.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier.fillMaxHeight()
                                            ) {
                                                Text(
                                                    text = "$fullname",
                                                    fontSize = 16.sp,
                                                    color = Color(0xffea9010),
                                                    fontWeight = FontWeight.W500,
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = "${item.taskTitle}",
                                                    fontSize = 15.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.W400,
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = "${item.taskContent}",
                                                    fontSize = 14.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.W300,
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = "${df1.format(item.taskStart.toDate())} - ${df1.format(item.taskEnd.toDate())}",
                                                    fontSize = 14.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.W300,
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))


                                                Text(
                                                    text = "${item.taskStatus}",
                                                    fontSize = 14.sp,
                                                    color = if (item.taskStatus == "Đang thực hiện") Color(0xFFEA9010) else if (item.taskStatus == "Hoàn thành") Color.Green else Color.Red,
                                                    fontWeight = FontWeight.W300,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }

                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = null,
                                            modifier = Modifier.size(26.dp),
                                            tint = Color.LightGray
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    TaskListScreen(
        userID = "userID",
        backTaskScreen = {}
    )
}
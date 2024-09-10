package com.example.quanlynhansu.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInOutScreen(
    userID: String,
    role: String,
    backHomeScreen: () -> Unit,
    showCheckInOutHistoryScreen: (userID: String) -> Unit,
    showCheckInOutListScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFD6229),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Chấm công", fontSize = 18.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backHomeScreen()
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
                    text = "QUẢN LÝ CHẤM CÔNG",
                    color = Color(0xffFD6229),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Giao diện ${role.lowercase()}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Chức năng chính",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W300
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = Color(0xFFFD6229)
                    )
                }

                Text(
                    text = "Xem tất cả",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Button(
                        onClick = {
                            showCheckInOutHistoryScreen(userID)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFFFD6229)
                        ),
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(6.dp),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                        modifier = Modifier.size(170.dp, 170.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_fingerprint_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xffea9010)
                            )
                            Text(
                                text = "Lịch sử chấm công",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Các lần thực hiện chấm công",
                                fontSize = 14.sp, fontWeight = FontWeight.W300,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFFFD6229)
                        ),
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(6.dp),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                        modifier = Modifier.size(170.dp, 170.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xffea9010)
                            )
                            Text(
                                text = "Báo cáo chấm công",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Gửi báo cáo chấm công cho cấp trên",
                                fontSize = 14.sp, fontWeight = FontWeight.W300,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // admin
            if (role.equals("Quản trị")) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Chức năng nâng cao",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W300
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = Color(0xFFFD6229)
                        )
                    }

                    Text(
                        text = "Xem tất cả",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Button(
                            onClick = {
                                showCheckInOutListScreen()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFFD6229)
                            ),
                            border = BorderStroke(1.dp, color = Color.Transparent),
                            shape = RoundedCornerShape(6.dp),
                            elevation = ButtonDefaults.buttonElevation(10.dp),
                            modifier = Modifier.size(170.dp, 170.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xffea9010)
                                )
                                Text(
                                    text = "Bảng công nhân viên",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Thông tin chi tiết nội dung bảng công",
                                    fontSize = 14.sp, fontWeight = FontWeight.W300,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(
                            onClick = {
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFFD6229)
                            ),
                            border = BorderStroke(1.dp, color = Color.Transparent),
                            shape = RoundedCornerShape(6.dp),
                            elevation = ButtonDefaults.buttonElevation(10.dp),
                            modifier = Modifier.size(170.dp, 170.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xffea9010)
                                )
                                Text(
                                    text = "Yêu cầu chấm công",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Phê duyệt yêu cầu chấm công của nhân viên",
                                    fontSize = 14.sp, fontWeight = FontWeight.W300,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(
                            onClick = {
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFFD6229)
                            ),
                            border = BorderStroke(1.dp, color = Color.Transparent),
                            shape = RoundedCornerShape(6.dp),
                            elevation = ButtonDefaults.buttonElevation(10.dp),
                            modifier = Modifier.size(170.dp, 170.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Build,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xffea9010)
                                )
                                Text(
                                    text = "Thống kê chấm công",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W600,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Tổng quan về số lần chấm công",
                                    fontSize = 14.sp, fontWeight = FontWeight.W300,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CheckInOutScreenPreview() {
    CheckInOutScreen(
        userID = "userID",
        role = "role",
        backHomeScreen = {},
        showCheckInOutHistoryScreen = {},
        showCheckInOutListScreen = {}
    )
}
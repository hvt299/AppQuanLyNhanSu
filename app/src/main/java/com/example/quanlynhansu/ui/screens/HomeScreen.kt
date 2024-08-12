package com.example.quanlynhansu.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.R
import com.example.quanlynhansu.firebase.getEmployeeQuantity
import com.example.quanlynhansu.ui.AlertDialogComponent
import com.example.quanlynhansu.ui.TextComponent
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userID: String,
    fullname: String,
    position: String,
    role: String,
    backLoginScreen: () -> Unit,
    showInfoScreen: (userID: String, role: String) -> Unit,
    showStatisticalEmployeeScreen: () -> Unit,
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    var employeeQuantity by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            employeeQuantity = getEmployeeQuantity()
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            TextComponent(
                                textValue = fullname,
                                color = Color.White,
                                textSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier
                            )

                            TextComponent(
                                textValue = position,
                                color = Color.White,
                                textSize = 14.sp,
                                fontWeight = FontWeight.W300,
                                modifier = Modifier
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xffea9010),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null
                        )
                    }
                }
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
            val currentTime by remember {
                mutableStateOf(LocalTime.now())
            }
            val formattedTime by remember {
                derivedStateOf {
                    DateTimeFormatter
                        .ofPattern("HH:mm")
                        .format(currentTime)
                }
            }

            var checkIn by remember {
                mutableStateOf(false)
            }
            var titleCheckInOut by remember {
                mutableStateOf("Vào ca")
            }
            var checkInOutBtnContainerColor by remember {
                mutableStateOf(Color(0xFFFD6229))
            }
            var checkInOutBtnContentColor by remember {
                mutableStateOf(Color.White)
            }
            var checkInOutIconContainerColor by remember {
                mutableStateOf(Color.White)
            }
            var checkInOutIconContentColor by remember {
                mutableStateOf(Color(0xFFEA9010))
            }

            var openCheckInAlertDialog by remember {
                mutableStateOf(false)
            }
            var openCheckOutAlertDialog by remember {
                mutableStateOf(false)
            }
            var openLogoutAlertDialog by remember {
                mutableStateOf(false)
            }

            when {
                openCheckInAlertDialog -> {
                    AlertDialogComponent(
                        onDismissRequest = {
                            openCheckInAlertDialog = false
                        },
                        onConfirmation = {
                            checkIn = true
                            openCheckInAlertDialog = false
                            checkInOutBtnContainerColor = Color.White
                            checkInOutBtnContentColor = Color(0xFFFD6229)
                            checkInOutIconContainerColor = Color(0xFFEA9010)
                            checkInOutIconContentColor = Color.White
                            titleCheckInOut = "Ra ca"
                            Toast.makeText(context, "Bạn đã check-in thành công!", Toast.LENGTH_SHORT).show()
                        },
                        dialogTitle = "Bạn chắc chắn muốn check-in hay không?",
                        dialogText = "Nếu nhấn vào OK, bạn sẽ không thể hoàn tác thao tác này được",
                        icon = Icons.Default.Warning
                    )
                }

                openCheckOutAlertDialog -> {
                    AlertDialogComponent(
                        onDismissRequest = {
                            openCheckOutAlertDialog = false
                        },
                        onConfirmation = {
                            checkIn = false
                            openCheckOutAlertDialog = false
                            checkInOutBtnContainerColor = Color(0xFFFD6229)
                            checkInOutBtnContentColor = Color.White
                            checkInOutIconContainerColor = Color.White
                            checkInOutIconContentColor = Color(0xFFEA9010)
                            titleCheckInOut = "Vào ca"
                            Toast.makeText(context, "Bạn đã check-out thành công!", Toast.LENGTH_SHORT).show()
                        },
                        dialogTitle = "Bạn chắc chắn muốn check-out hay không?",
                        dialogText = "Nếu nhấn vào OK, bạn sẽ không thể hoàn tác thao tác này được",
                        icon = Icons.Default.Warning
                    )
                }

                openLogoutAlertDialog -> {
                    AlertDialogComponent(
                        onDismissRequest = {
                            openLogoutAlertDialog = false
                        },
                        onConfirmation = {
                            openLogoutAlertDialog = false
                            Toast.makeText(context, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show()
                            backLoginScreen()
                        },
                        dialogTitle = "Bạn chắc chắn muốn đăng xuất hay không?",
                        dialogText = "Nếu nhấn vào OK, bạn sẽ không thể hoàn tác thao tác này được",
                        icon = Icons.Default.Warning
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "Trang chủ",
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

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ) {
                Button(
                    onClick = {
                        if (!checkIn) {
                            openCheckInAlertDialog = !openCheckInAlertDialog
                        } else {
                            openCheckOutAlertDialog = !openCheckOutAlertDialog
                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = checkInOutBtnContainerColor,
                        contentColor = checkInOutBtnContentColor
                    ),
                    border = BorderStroke(1.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    modifier = Modifier.height(80.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = titleCheckInOut,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = formattedTime,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center
                            )
                        }

                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = checkInOutIconContainerColor,
                                contentColor = checkInOutIconContentColor
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_fingerprint_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

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
//                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
            ) {
                Row(
//                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
//                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = { /*TODO*/ },
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
                                text = "CHẤM CÔNG",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Lịch sử chấm công, đăng ký nghỉ...",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W300,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = { /*TODO*/ },
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
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xffea9010)
                            )
                            Text(
                                text = "BẢNG LƯƠNG",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Tra cứu chi tiết nội dung bảng lương",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W300,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

//                Spacer(modifier = Modifier.height(20.dp))

                Row(
//                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
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
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xffea9010)
                            )
                            Text(
                                text = "CÔNG VIỆC",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Theo dõi, quản lý công việc cá nhân",
                                fontSize = 14.sp, fontWeight = FontWeight.W300,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            showInfoScreen(userID, role)
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
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xffea9010)
                            )
                            Text(
                                text = "HỒ SƠ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Bổ sung các thông tin hồ sơ bản thân",
                                fontSize = 14.sp, fontWeight = FontWeight.W300,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Button(
                    onClick = {
                        openLogoutAlertDialog = !openLogoutAlertDialog
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEEEEEE),
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.LightGray
                        )
                        Text(
                            text = "ĐĂNG XUẤT",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Thoát tài khoản và ứng dụng",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W300,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // admin
            if (role.equals("Quản trị")) {
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Tổng quan",
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
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                ) {
                    Button(
                        onClick = {
                            showStatisticalEmployeeScreen()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                        modifier = Modifier.height(80.dp)
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
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        text = "Nhân sự hiện tại",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "$employeeQuantity",
                                        fontSize = 22.sp,
                                        color = Color(0xffea9010),
                                        fontWeight = FontWeight.W400,
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

                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                ) {
                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                        modifier = Modifier.height(80.dp)
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
                                        imageVector = Icons.Default.Face,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        text = "Đang làm việc",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "5",
                                        fontSize = 22.sp,
                                        color = Color(0xffea9010),
                                        fontWeight = FontWeight.W400,
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

                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                        modifier = Modifier.height(80.dp)
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
                                        imageVector = Icons.Default.MailOutline,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        text = "Công việc hôm nay",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "10",
                                        fontSize = 22.sp,
                                        color = Color(0xffea9010),
                                        fontWeight = FontWeight.W400,
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

                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                        modifier = Modifier.height(80.dp)
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
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        text = "Doanh thu (VNĐ)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "50.000.000",
                                        fontSize = 22.sp,
                                        color = Color(0xffea9010),
                                        fontWeight = FontWeight.W400,
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

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        userID = "userID",
        fullname = "fullname",
        position = "position",
        role = "role",
        backLoginScreen = {},
        showInfoScreen = {userID, role -> },
        showStatisticalEmployeeScreen = {}
    )
}
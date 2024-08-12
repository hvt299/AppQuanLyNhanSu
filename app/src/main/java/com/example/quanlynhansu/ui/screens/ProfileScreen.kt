package com.example.quanlynhansu.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.models.Employee
import com.example.quanlynhansu.models.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userID: String,
    backPreviousScreen: () -> Unit
) {
//    var userList = mutableStateListOf<User?>()
    val userList = remember {
        mutableStateListOf<User?>()
    }
    val employeeList = remember {
        mutableStateListOf<Employee?>()
    }

    val df1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val df2 = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    db.collection("User").get()
        .addOnSuccessListener {
            if (!it.isEmpty) {
                val uList = it.documents
                for (d in uList) {
                    val u: User? = d.toObject(User::class.java)
                    u?.userID = d.id
                    if (userID == u?.userID) {
                        userList.add(u)
                        break
                    }
                }
            }
        }
        .addOnFailureListener {}
    db.collection("Employee").get()
        .addOnSuccessListener {
            if (!it.isEmpty) {
                val eList = it.documents
                for (d in eList) {
                    val e: Employee? = d.toObject(Employee::class.java)
                    e?.employeeID = d.id
                    if (userID == e?.userID) {
                        employeeList.add(e)
                        break
                    }
                }
            }
        }
        .addOnFailureListener {}

    var status = ""
    var role = ""
    var lastLogin = Timestamp.now()
    var createdAt = Timestamp.now()
    var updatedAt = Timestamp.now()
    for (user in userList) {
        if (user != null) {
            status = user.status
            role = user.role
            lastLogin = user.lastLogin
            createdAt = user.createdAt
            updatedAt = user.updatedAt
        }
    }
    var employeeID = ""
    var fullname = ""
    var gender = ""
    var dateOfBirth = Timestamp.now()
    var idCard = ""
    var placeOfBirth = ""
    var placeOfResidence = ""
    var cityOrProvince = ""
    var district = ""
    var wardOrCommune = ""
    var phoneNumber = ""
    var emailAddress = ""
    var department = ""
    var position = ""
    var startDate = Timestamp.now()
    var endDate = Timestamp.now()
    for (employee in employeeList) {
        if (employee != null) {
            employeeID = employee.employeeID
            fullname = employee.fullname
            gender = employee.gender.toString()
            dateOfBirth = employee.dateOfBirth
            idCard = employee.idCard.toString()
            placeOfBirth = employee.placeOfBirth.toString()
            placeOfResidence = employee.placeOfResidence.toString()
            cityOrProvince = employee.cityOrProvince.toString()
            district = employee.district.toString()
            wardOrCommune = employee.wardOrCommune.toString()
            phoneNumber = employee.phoneNumber.toString()
            emailAddress = employee.emailAddress.toString()
            department = employee.department.toString()
            position = employee.position
            startDate = employee.startDate
            endDate = employee.endDate
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
                        backPreviousScreen()
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
            Column {
                Text(
                    text = "THÔNG TIN CÁ NHÂN",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5067FF))
                        .padding(4.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Mã nhân viên",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = employeeID,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Họ và tên",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = fullname,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Giới tính",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = gender,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Ngày sinh",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = df1.format(dateOfBirth.toDate()),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "CMND/CCCD/Hộ chiếu",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = idCard,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Nơi sinh",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = placeOfBirth,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Nơi thường trú",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = placeOfResidence,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Tỉnh, thành phố",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = cityOrProvince,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Quận, huyện",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = district,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Phường, xã",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = wardOrCommune,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Điện thoại",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = phoneNumber,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Email",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = emailAddress,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }
            }
            Column {
                Text(
                    text = "THÔNG TIN CHỨC DANH",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5067FF))
                        .padding(4.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Phòng ban",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = department,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Chức vụ",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = position,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Ngày bắt đầu làm",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = df1.format(startDate.toDate()),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Ngày nghỉ việc",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = df1.format(endDate.toDate()),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }
            }
            Column {
                Text(
                    text = "THÔNG TIN TÀI KHOẢN",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5067FF))
                        .padding(4.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Mã tài khoản người dùng",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = userID,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Trạng thái tài khoản",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = status,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Nhóm truy cập",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = role,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Lần cuối đăng nhập",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = df2.format(lastLogin.toDate()),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Lần đầu đăng nhập",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = df2.format(createdAt.toDate()),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }

                Divider(color = Color.LightGray)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Lần cập nhật gần nhất",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                    Text(
                        text = df2.format(updatedAt.toDate()),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        userID = "userID",
        backPreviousScreen = {}
    )
}
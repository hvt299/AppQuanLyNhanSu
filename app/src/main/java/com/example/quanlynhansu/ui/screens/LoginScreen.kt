package com.example.quanlynhansu.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.R
import com.example.quanlynhansu.data.LoginDataUiEvents
import com.example.quanlynhansu.firebase.encodeToMD5
import com.example.quanlynhansu.models.Employee
import com.example.quanlynhansu.models.User
import com.example.quanlynhansu.ui.ButtonComponent
import com.example.quanlynhansu.ui.ImageComponent
import com.example.quanlynhansu.ui.LoginViewModel
import com.example.quanlynhansu.ui.TextComponent
import com.example.quanlynhansu.ui.TextFieldComponent
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    showHomeScreen: (userID: String, fullname: String, position: String, role: String) -> Unit,
    showRegisterScreen: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        var anhienmatkhau by rememberSaveable {
            mutableStateOf(false)
        }

        val currentDateTime: Timestamp = Timestamp.now()

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ImageComponent(size = 180.dp, image = R.drawable.logo)

            TextComponent(
                textValue = "ỨNG DỤNG QUẢN LÝ NHÂN SỰ",
                color = Color(0xffFD6229),
                textSize = 22.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(80.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextFieldComponent(
                    defaultValue = "",
                    onTextChanged = {
                        loginViewModel.onEvent(
                            LoginDataUiEvents.UsernameEntered(it)
                        )
                    },
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
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray,
                        focusedLeadingIconColor = Color(0xffea9010),
                        unfocusedLeadingIconColor = Color(0xffea9010)
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    defaultValue = "",
                    onTextChanged = {
                        loginViewModel.onEvent(
                            LoginDataUiEvents.PasswordEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholderValue = "Mật khẩu",
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xffA26F38),
                        unfocusedTextColor = Color(0xffA26F38),
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray,
                        focusedTrailingIconColor = Color.Gray,
                        unfocusedTrailingIconColor = Color.Gray,
                        focusedLeadingIconColor = Color(0xffea9010),
                        unfocusedLeadingIconColor = Color(0xffea9010)
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            anhienmatkhau = !anhienmatkhau
                        }) {
                            val id =
                                if (anhienmatkhau) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = null
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    visualTransformation = if (anhienmatkhau) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    onClick = {
                        if (loginViewModel.uiState.value.usernameEntered.isNotEmpty()
                            && loginViewModel.uiState.value.passwordEntered.isNotEmpty()
                        ) {
                            val updatedUser = User("", "", "", "", "", currentDateTime, currentDateTime, currentDateTime)
                            val updatedEmployee = Employee("", "", "", "", currentDateTime, "", "", "", "", "", "", "", "", "", "", currentDateTime, currentDateTime)

                            val usernameEntered = loginViewModel.uiState.value.usernameEntered
                            val passwordEntered = encodeToMD5(loginViewModel.uiState.value.passwordEntered)
                            val userList = mutableStateListOf<User?>()
                            val employeeList = mutableStateListOf<Employee?>()
                            val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                            db.collection("User").get()
                                .addOnSuccessListener {
                                    if (!it.isEmpty) {
                                        val UserList = it.documents
                                        for (d in UserList) {
                                            val u: User? = d.toObject(User::class.java)
                                            u?.userID = d.id
                                            if (usernameEntered.equals(u?.username) && passwordEntered.equals(u?.password)){
                                                updatedUser.userID = u?.userID.toString()
                                                updatedUser.username = u?.username.toString()
                                                updatedUser.password = u?.password.toString()
                                                updatedUser.status = u?.status.toString()
                                                updatedUser.role = u?.role.toString()
                                                updatedUser.lastLogin = currentDateTime
                                                updatedUser.createdAt = u?.createdAt!!
                                                updatedUser.updatedAt = u?.updatedAt!!
                                                userList.add(u)
                                                break
                                            }
                                        }
                                        if (userList.isNotEmpty()) {
                                            db.collection("Employee").get()
                                                .addOnSuccessListener {
                                                    if(!it.isEmpty) {
                                                        val EmployeeList = it.documents
                                                        for (d in EmployeeList) {
                                                            val e: Employee? = d.toObject(Employee::class.java)
                                                            e?.employeeID = d.id
                                                            if (usernameEntered.equals(e?.userID)
                                                                || updatedUser.userID.equals(e?.userID)) {

                                                                updatedEmployee.employeeID = e?.employeeID.toString()
                                                                updatedEmployee.userID = updatedUser.userID
                                                                updatedEmployee.fullname = e?.fullname.toString()
                                                                updatedEmployee.gender = e?.gender.toString()
                                                                updatedEmployee.dateOfBirth = e?.dateOfBirth!!
                                                                updatedEmployee.idCard = e?.idCard.toString()
                                                                updatedEmployee.placeOfBirth = e?.placeOfBirth.toString()
                                                                updatedEmployee.placeOfResidence = e?.placeOfResidence.toString()
                                                                updatedEmployee.cityOrProvince = e?.cityOrProvince.toString()
                                                                updatedEmployee.district = e?.district.toString()
                                                                updatedEmployee.wardOrCommune = e?.wardOrCommune.toString()
                                                                updatedEmployee.phoneNumber = e?.phoneNumber.toString()
                                                                updatedEmployee.emailAddress = e?.emailAddress.toString()
                                                                updatedEmployee.department = e?.department.toString()
                                                                updatedEmployee.position = e?.position.toString()
                                                                updatedEmployee.startDate = e?.startDate!!
                                                                updatedEmployee.endDate = e?.endDate!!

                                                                employeeList.add(e)
                                                                break
                                                            }
                                                        }
                                                        if (employeeList.isNotEmpty()) {
                                                            db.collection("User")
                                                                .document(updatedUser.userID)
                                                                .set(updatedUser)
                                                            db.collection("Employee")
                                                                .document(updatedEmployee.employeeID)
                                                                .set(updatedEmployee)
                                                            loginViewModel.uiState.value.usernameEntered = ""
                                                            loginViewModel.uiState.value.passwordEntered = ""
                                                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                                            showHomeScreen(updatedUser.userID, updatedEmployee.fullname, updatedEmployee.position, updatedUser.role)
                                                        }
                                                    }
                                                }
                                        } else {
                                            Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener {}
                        } else {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                        }
                    },
                    containerColor = Color(0xffFD6229),
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
        }

        Row {
            TextComponent(
                textValue = "Chưa có tài khoản? ",
                color = Color.Black,
                textSize = 14.sp,
                fontWeight = FontWeight.W300,
                modifier = Modifier
            )

            TextComponent(
                textValue = "Đăng kí ngay!",
                color = Color(0xffFD6229),
                textSize = 14.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .clickable {
                        showRegisterScreen()
                    }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        loginViewModel = LoginViewModel(),
        showHomeScreen = {userID, fullname, position, role -> },
        showRegisterScreen = {}
    )
}
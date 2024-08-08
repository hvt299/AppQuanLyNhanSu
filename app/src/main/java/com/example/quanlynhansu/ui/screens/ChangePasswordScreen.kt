package com.example.quanlynhansu.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.R
import com.example.quanlynhansu.data.ChangePasswordDataUiEvents
import com.example.quanlynhansu.firebase.encodeToMD5
import com.example.quanlynhansu.models.User
import com.example.quanlynhansu.ui.ButtonComponent
import com.example.quanlynhansu.ui.ChangePasswordViewModel
import com.example.quanlynhansu.ui.TextFieldComponent
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    userID: String,
    changePasswordViewModel: ChangePasswordViewModel,
    backInfoScreen: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var anhienmatkhau by rememberSaveable {
        mutableStateOf(false)
    }

    val currentDateTime: LocalDateTime = LocalDateTime.now()
    val formattedCurrentDateTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm")
                .format(currentDateTime)
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
                    text = "THAY ĐỔI MẬT KHẨU",
                    color = Color(0xffFD6229),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                TextFieldComponent(
                    defaultValue = "",
                    onTextChanged = {
                        changePasswordViewModel.onEvent(
                            ChangePasswordDataUiEvents.OldPasswordEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholderValue = "Mật khẩu hiện tại",
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xffA26F38),
                        unfocusedTextColor = Color(0xffA26F38),
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    leadingIcon = null,
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
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    visualTransformation = if (anhienmatkhau) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    defaultValue = "",
                    onTextChanged = {
                        changePasswordViewModel.onEvent(
                            ChangePasswordDataUiEvents.NewPasswordEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholderValue = "Mật khẩu mới",
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xffA26F38),
                        unfocusedTextColor = Color(0xffA26F38),
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    leadingIcon = null,
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
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    visualTransformation = if (anhienmatkhau) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    defaultValue =  "",
                    onTextChanged = {
                        changePasswordViewModel.onEvent(
                            ChangePasswordDataUiEvents.ReNewPasswordEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholderValue = "Xác nhận mật khẩu mới",
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xffA26F38),
                        unfocusedTextColor = Color(0xffA26F38),
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    leadingIcon = null,
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
                        if (changePasswordViewModel.uiState.value.oldpasswordEntered.isNotEmpty()
                            && changePasswordViewModel.uiState.value.newpasswordEntered.isNotEmpty()) {
                            if (changePasswordViewModel.uiState.value.renewpasswordEntered
                                == changePasswordViewModel.uiState.value.newpasswordEntered) {

                                val updatedUser = User("", "", "", "", "", "", "", "")
                                val userList = mutableStateListOf<User?>()
                                val db: FirebaseFirestore = FirebaseFirestore.getInstance()

                                db.collection("User").get()
                                    .addOnSuccessListener {
                                        if (!it.isEmpty) {
                                            val UserList = it.documents
                                            for (d in UserList) {
                                                val u: User? = d.toObject(User::class.java)
                                                u?.userID = d.id
                                                if (userID == u?.userID
                                                    && encodeToMD5(changePasswordViewModel.uiState.value.oldpasswordEntered)
                                                    .equals(u?.password)
                                                ) {
                                                    updatedUser.userID = u?.userID.toString()
                                                    updatedUser.username = u?.username.toString()
                                                    updatedUser.password = encodeToMD5(changePasswordViewModel.uiState.value.newpasswordEntered)
                                                    updatedUser.status = u?.status.toString()
                                                    updatedUser.role = u?.role.toString()
                                                    updatedUser.lastLogin = u?.lastLogin.toString()
                                                    updatedUser.createdAt = u?.createdAt.toString()
                                                    updatedUser.updatedAt = formattedCurrentDateTime
                                                    userList.add(u)
                                                    break
                                                }
                                            }
                                            if (userList.isNotEmpty()) {
                                                db.collection("User")
                                                    .document(updatedUser.userID)
                                                    .set(updatedUser)
                                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                                backInfoScreen()
                                            } else {
                                                Toast.makeText(context, "Mật khẩu hiện tại không chính xác", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    .addOnFailureListener {}
                            } else {
                                Toast.makeText(context, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show()
                            }
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
                    textValue = "Cập nhật",
                    textSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(
        userID = "userID",
        changePasswordViewModel = ChangePasswordViewModel(),
        backInfoScreen = {}
    )
}
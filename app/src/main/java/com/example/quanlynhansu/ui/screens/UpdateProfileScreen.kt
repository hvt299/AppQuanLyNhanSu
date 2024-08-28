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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.quanlynhansu.data.UpdateProfileDataUiEvents
import com.example.quanlynhansu.firebase.encodeToMD5
import com.example.quanlynhansu.firebase.getEmployeeByUserID
import com.example.quanlynhansu.firebase.getUserByUserID
import com.example.quanlynhansu.models.Employee
import com.example.quanlynhansu.models.User
import com.example.quanlynhansu.ui.ButtonComponent
import com.example.quanlynhansu.ui.UpdateProfileViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    userID: String,
    role: String,
    updateProfileViewModel: UpdateProfileViewModel,
    backPreviousScreen: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var anhienmatkhau by rememberSaveable {
        mutableStateOf(false)
    }
    val df = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val df1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val df2 = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var lastLogin by remember {
        mutableStateOf(
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm")
                .format(LocalDateTime.now())
        )
    }
    var createdAt by remember {
        mutableStateOf(
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm")
                .format(LocalDateTime.now())
        )
    }
    val updatedAt by remember {
        mutableStateOf(
            DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm")
            .format(LocalDateTime.now())
        )
    }
    var employeeID by remember {
        mutableStateOf("")
    }
    var fullnameEntered by remember {
        mutableStateOf("")
    }
    var genderSelected by remember {
        mutableStateOf("")
    }
    var pickedDateOfBirth by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedStartDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedEndDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var idcardEntered by remember {
        mutableStateOf("")
    }
    var placeofbirthEntered by remember {
        mutableStateOf("")
    }
    var placeofresidenceEntered by remember {
        mutableStateOf("")
    }
    var cityorprovinceEntered by remember {
        mutableStateOf("")
    }
    var districtEntered by remember {
        mutableStateOf("")
    }
    var wardorcommuneEntered by remember {
        mutableStateOf("")
    }
    var phonenumberEntered by remember {
        mutableStateOf("")
    }
    var emailaddressEntered by remember {
        mutableStateOf("")
    }
    var departmentSelected by remember {
        mutableStateOf("")
    }
    var positionSelected by remember {
        mutableStateOf("")
    }
    var statusAccountSelected by remember {
        mutableStateOf("")
    }
    var roleAccountSelected by remember {
        mutableStateOf("")
    }

    val formattedDateOfBirth by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedDateOfBirth)
        }
    }
    val formattedStartDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedStartDate)
        }
    }
    val formattedEndDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedEndDate)
        }
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val user = getUserByUserID(userID)
            val employee = getEmployeeByUserID(userID)

            username = user.username
            updateProfileViewModel.uiState.value.usernameEntered = user.username
            password = user.password
            updateProfileViewModel.uiState.value.passwordEntered = user.password
            if (userID != "0") {
                lastLogin = df2.format(user.lastLogin.toDate())
                createdAt = df2.format(user.createdAt.toDate())
                pickedDateOfBirth = LocalDate.parse(df1.format(employee.dateOfBirth.toDate()), df)
                pickedStartDate = LocalDate.parse(df1.format(employee.startDate.toDate()), df)
                pickedEndDate = LocalDate.parse(df1.format(employee.endDate.toDate()), df)
            }
            employeeID = employee.employeeID

            fullnameEntered = employee.fullname
            updateProfileViewModel.uiState.value.fullnameEntered = employee.fullname

            genderSelected = employee.gender.toString()

            idcardEntered = employee.idCard.toString()
            updateProfileViewModel.uiState.value.idcardEntered = employee.idCard.toString()

            placeofbirthEntered = employee.placeOfBirth.toString()
            updateProfileViewModel.uiState.value.placeofbirthEntered =
                employee.placeOfBirth.toString()

            placeofresidenceEntered = employee.placeOfResidence.toString()
            updateProfileViewModel.uiState.value.placeofresidenceEntered =
                employee.placeOfResidence.toString()

            cityorprovinceEntered = employee.cityOrProvince.toString()
            updateProfileViewModel.uiState.value.cityorprovinceEntered =
                employee.cityOrProvince.toString()

            districtEntered = employee.district.toString()
            updateProfileViewModel.uiState.value.districtEntered = employee.district.toString()

            wardorcommuneEntered = employee.wardOrCommune.toString()
            updateProfileViewModel.uiState.value.wardorcommuneEntered =
                employee.wardOrCommune.toString()

            phonenumberEntered = employee.phoneNumber.toString()
            updateProfileViewModel.uiState.value.phonenumberEntered =
                employee.phoneNumber.toString()

            emailaddressEntered = employee.emailAddress.toString()
            updateProfileViewModel.uiState.value.emailaddressEntered =
                employee.emailAddress.toString()

            departmentSelected = employee.department.toString()
            positionSelected = employee.position
            statusAccountSelected = user.status
            roleAccountSelected = user.role
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
                    Text(text = if (userID == "0") "Thêm thông tin hồ sơ" else "Cập nhật thông tin hồ sơ", fontSize = 18.sp, color = Color.White)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            // gender
            var genderExpanded by remember {
                mutableStateOf(false)
            }
            val genderList = arrayOf(
                "Nam",
                "Nữ",
                "Khác"
            )

            // department
            var departmentExpanded by remember {
                mutableStateOf(false)
            }
            val departmentList = arrayOf(
                "Phòng điều hành quản lý",
                "Phòng tổ chức nhân sự",
                "Phòng hành chính",
                "Phòng tài chính kế toán",
                "Phòng công nghệ thông tin",
                "Phòng kinh doanh",
                "Phòng đầu tư kỹ thuật công nghệ",
                "Khác"
            )

            // position
            var positionExpanded by remember {
                mutableStateOf(false)
            }
            val positionList = arrayOf(
                "Giám đốc điều hành",
                "Giám đốc tài chính",
                "Giám đốc marketing",
                "Giám đốc pháp lý",
                "Giám đốc thương mại",
                "Giám đốc vận hành",
                "Trưởng phòng nhân sự",
                "Quản lý công nghệ thông tin",
                "Trưởng phòng marketing",
                "Quản lý sản phẩm",
                "Quản lý bán hàng",
                "Lãnh đạo nhóm",
                "Nhân viên bán hàng",
                "Nhân viên kế toán",
                "Kỹ sư phần mềm",
                "Nhân viên nhân sự",
                "Chuyên viên phân tích nghiệp vụ",
                "Khác"
            )

            // status account
            var statusAccountExpanded by remember {
                mutableStateOf(false)
            }
            val statusAccountList = arrayOf(
                "Đang hoạt động",
                "Chờ duyệt",
                "Tạm khóa",
                "Bị chặn"
            )

            // role account
            var roleAccountExpanded by remember {
                mutableStateOf(false)
            }
            val roleAccountList = arrayOf(
                "Quản trị",
                "Nhân viên"
            )

            // date & time picker dialogs
            val dateOfBirthDialogState = rememberMaterialDialogState()
            val startDateDialogState = rememberMaterialDialogState()
            val endDateDialogState = rememberMaterialDialogState()

            MaterialDialog(
                dialogState = dateOfBirthDialogState,
                buttons = {
                    positiveButton(
                        text = "OK",
                        textStyle = TextStyle(
                            color = Color(0xFFFD6229),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                    negativeButton(
                        text = "Hủy",
                        textStyle = TextStyle(
                            color = Color(0xFFFD6229),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                }
            ) {
                datepicker(
                    initialDate = pickedDateOfBirth,
                    title = "Chọn ngày sinh",
                    colors = DatePickerDefaults.colors(
                        headerBackgroundColor = Color(0xFFFD6229),
                        dateActiveBackgroundColor = Color(0xFFFD6229)
                    ),
                    allowedDateValidator = {
                        it.isBefore(LocalDate.now()) || it.isEqual(LocalDate.now())
                    }
                ) {
                    pickedDateOfBirth = it
                    updateProfileViewModel.uiState.value.dateofbirthSelected = it
                }
            }

            MaterialDialog(
                dialogState = startDateDialogState,
                buttons = {
                    positiveButton(
                        text = "OK",
                        textStyle = TextStyle(
                            color = Color(0xFFFD6229),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                    negativeButton(
                        text = "Hủy",
                        textStyle = TextStyle(
                            color = Color(0xFFFD6229),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                }
            ) {
                datepicker(
                    initialDate = pickedStartDate,
                    title = "Chọn ngày bắt đầu làm",
                    colors = DatePickerDefaults.colors(
                        headerBackgroundColor = Color(0xFFFD6229),
                        dateActiveBackgroundColor = Color(0xFFFD6229)
                    )
                ) {
                    pickedStartDate = it
                    updateProfileViewModel.uiState.value.startdateSelected = it
                }
            }

            MaterialDialog(
                dialogState = endDateDialogState,
                buttons = {
                    positiveButton(
                        text = "OK",
                        textStyle = TextStyle(
                            color = Color(0xFFFD6229),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                    negativeButton(
                        text = "Hủy",
                        textStyle = TextStyle(
                            color = Color(0xFFFD6229),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                }
            ) {
                datepicker(
                    initialDate = pickedEndDate,
                    title = "Chọn ngày nghỉ việc",
                    colors = DatePickerDefaults.colors(
                        headerBackgroundColor = Color(0xFFFD6229),
                        dateActiveBackgroundColor = Color(0xFFFD6229)
                    )
                ) {
                    pickedEndDate = it
                    updateProfileViewModel.uiState.value.enddateSelected = it
                }
            }

//            var pickedTime by remember {
//                mutableStateOf(LocalTime.MIDNIGHT)
//            }
//            val formattedTime by remember {
//                derivedStateOf {
//                    DateTimeFormatter
//                        .ofPattern("HH:mm")
//                        .format(pickedTime)
//                }
//            }
//            val timeDialogState = rememberMaterialDialogState()
//            MaterialDialog(
//                dialogState = timeDialogState,
//                buttons = {
//                    positiveButton(
//                        text = "OK",
//                        textStyle = TextStyle(
//                            color = Color(0xFFFD6229),
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.W500
//                        )
//                    )
//                    negativeButton(
//                        text = "Hủy",
//                        textStyle = TextStyle(
//                            color = Color(0xFFFD6229),
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.W500
//                        )
//                    )
//                }
//            ) {
//                timepicker(
//                    initialTime = LocalTime.MIDNIGHT,
//                    title = "Chọn thời gian"
//                ) {
//                    pickedTime = it
//                }
//            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = fullnameEntered,
                    onValueChange = {
                        fullnameEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.FullnameEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Họ và tên", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                ExposedDropdownMenuBox(
                    expanded = genderExpanded,
                    onExpandedChange = {
                        genderExpanded = !genderExpanded
                    },
                    modifier = Modifier.width(350.dp)
                ) {
                    TextField(
                        value = genderSelected,
                        onValueChange = {
                            genderSelected = it
                        },
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
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                        ),
                        placeholder = {
                            Text(text = "Giới tính", fontSize = 14.sp)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                            Icon(
                                imageVector = if (genderExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                    )
                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = {
                            genderExpanded = false
                        }
                    ) {
                        genderList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    genderSelected = item
                                    updateProfileViewModel.uiState.value.genderSelected = item
                                    genderExpanded = false
                                    focusManager.clearFocus()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = formattedDateOfBirth,
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Ngày sinh", fontSize = 14.sp)
                    },
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
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                dateOfBirthDialogState.show()
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Gray
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = idcardEntered,
                    onValueChange = {
                        idcardEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.IDCardEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "CMND/CCCD/Hộ chiếu", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = placeofbirthEntered,
                    onValueChange = {
                        placeofbirthEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.PlaceOfBirthEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Nơi sinh", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = placeofresidenceEntered,
                    onValueChange = {
                        placeofresidenceEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.PlaceOfResidenceEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Nơi thường trú", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = cityorprovinceEntered,
                    onValueChange = {
                        cityorprovinceEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.CityOrProvinceEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Tỉnh, thành phố", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = districtEntered,
                    onValueChange = {
                        districtEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.DistrictEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Quận, huyện", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = wardorcommuneEntered,
                    onValueChange = {
                        wardorcommuneEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.WardOrCommuneEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Phường, xã", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = phonenumberEntered,
                    onValueChange = {
                        phonenumberEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.PhoneNumberEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Điện thoại", fontSize = 14.sp)
                    },
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = emailaddressEntered,
                    onValueChange = {
                        emailaddressEntered = it
                        updateProfileViewModel.onEvent(
                            UpdateProfileDataUiEvents.EmailAddressEntered(it)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    ),
                    placeholder = {
                        Text(text = "Email", fontSize = 14.sp)
                    },
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
                    leadingIcon = null,
                    trailingIcon = null,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.width(350.dp)
                )

                if (role != "Quản trị") {
                    Spacer(modifier = Modifier.height(40.dp))

                    ButtonComponent(
                        onClick = {
                            if (updateProfileViewModel.uiState.value.fullnameEntered.isNotEmpty()) {
                                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                                val user = User(userID, username, password, statusAccountSelected, roleAccountSelected, Timestamp(df2.parse(lastLogin)), Timestamp(df2.parse(createdAt)), Timestamp(df2.parse(updatedAt)))
                                val employee = Employee(
                                    employeeID, userID, updateProfileViewModel.uiState.value.fullnameEntered, genderSelected, Timestamp(df1.parse(formattedDateOfBirth)),
                                    updateProfileViewModel.uiState.value.idcardEntered, updateProfileViewModel.uiState.value.placeofbirthEntered, updateProfileViewModel.uiState.value.placeofresidenceEntered, updateProfileViewModel.uiState.value.cityorprovinceEntered,
                                    updateProfileViewModel.uiState.value.districtEntered, updateProfileViewModel.uiState.value.wardorcommuneEntered, updateProfileViewModel.uiState.value.phonenumberEntered, updateProfileViewModel.uiState.value.emailaddressEntered,
                                    departmentSelected, positionSelected, Timestamp(df1.parse(formattedStartDate)), Timestamp(df1.parse(formattedEndDate)))
                                db.collection("Employee").document(employeeID).set(employee)
                                    .addOnSuccessListener {
                                        db.collection("User").document(userID).set(user)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                                backPreviousScreen()
                                            }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
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
                Spacer(modifier = Modifier.height(20.dp))
            }

            // admin
            if (role == "Quản trị") {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

                    Spacer(modifier = Modifier.height(20.dp))

                    ExposedDropdownMenuBox(
                        expanded = departmentExpanded,
                        onExpandedChange = {
                            departmentExpanded = !departmentExpanded
                        },
                        modifier = Modifier.width(350.dp)
                    ) {
                        TextField(
                            value = departmentSelected,
                            onValueChange = {
                                departmentSelected = it
                            },
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
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                            ),
                            placeholder = {
                                Text(text = "Phòng ban", fontSize = 14.sp)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = departmentExpanded)
                                Icon(
                                    imageVector = if (departmentExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                        )
                        ExposedDropdownMenu(
                            expanded = departmentExpanded,
                            onDismissRequest = {
                                departmentExpanded = false
                            }
                        ) {
                            departmentList.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        departmentSelected = item
                                        updateProfileViewModel.uiState.value.departmentSelected =
                                            item
                                        departmentExpanded = false
                                        focusManager.clearFocus()
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ExposedDropdownMenuBox(
                        expanded = positionExpanded,
                        onExpandedChange = {
                            positionExpanded = !positionExpanded
                        },
                        modifier = Modifier.width(350.dp)
                    ) {
                        TextField(
                            value = positionSelected,
                            onValueChange = {
                                positionSelected = it
                            },
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
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                            ),
                            placeholder = {
                                Text(text = "Chức vụ", fontSize = 14.sp)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = positionExpanded)
                                Icon(
                                    imageVector = if (positionExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                        )
                        ExposedDropdownMenu(
                            expanded = positionExpanded,
                            onDismissRequest = {
                                positionExpanded = false
                            }
                        ) {
                            positionList.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        positionSelected = item
                                        updateProfileViewModel.uiState.value.positionSelected = item
                                        positionExpanded = false
                                        focusManager.clearFocus()
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = formattedStartDate,
                        onValueChange = {},
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400
                        ),
                        placeholder = {
                            Text(text = "Ngày bắt đầu làm", fontSize = 14.sp)
                        },
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
                        readOnly = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        leadingIcon = null,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    startDateDialogState.show()
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Gray
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.width(350.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = formattedEndDate,
                        onValueChange = {},
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400
                        ),
                        placeholder = {
                            Text(text = "Ngày nghỉ việc", fontSize = 14.sp)
                        },
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
                        readOnly = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        leadingIcon = null,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    endDateDialogState.show()
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Gray
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.width(350.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

                    if (userID == "0") {
                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = username,
                            onValueChange = {
                                username = it
                                updateProfileViewModel.onEvent(
                                    UpdateProfileDataUiEvents.UsernameEntered(it)
                                )
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            ),
                            placeholder = {
                                Text(text = "Tên đăng nhập", fontSize = 14.sp)
                            },
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
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            leadingIcon = null,
                            trailingIcon = null,
                            visualTransformation = VisualTransformation.None,
                            modifier = Modifier.width(350.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                                updateProfileViewModel.onEvent(
                                    UpdateProfileDataUiEvents.PasswordEntered(it)
                                )
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            ),
                            placeholder = {
                                Text(text = "Mật khẩu", fontSize = 14.sp)
                            },
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
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ExposedDropdownMenuBox(
                        expanded = statusAccountExpanded,
                        onExpandedChange = {
                            statusAccountExpanded = !statusAccountExpanded
                        },
                        modifier = Modifier.width(350.dp)
                    ) {
                        TextField(
                            value = statusAccountSelected,
                            onValueChange = {
                                statusAccountSelected = it
                            },
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
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                            ),
                            placeholder = {
                                Text(text = "Trạng thái tài khoản", fontSize = 14.sp)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusAccountExpanded)
                                Icon(
                                    imageVector = if (statusAccountExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                        )
                        ExposedDropdownMenu(
                            expanded = statusAccountExpanded,
                            onDismissRequest = {
                                statusAccountExpanded = false
                            }
                        ) {
                            statusAccountList.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        statusAccountSelected = item
                                        updateProfileViewModel.uiState.value.statusaccountSelected = item
                                        statusAccountExpanded = false
                                        focusManager.clearFocus()
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ExposedDropdownMenuBox(
                        expanded = roleAccountExpanded,
                        onExpandedChange = {
                            roleAccountExpanded = !roleAccountExpanded
                        },
                        modifier = Modifier.width(350.dp)
                    ) {
                        TextField(
                            value = roleAccountSelected,
                            onValueChange = {
                                roleAccountSelected = it
                            },
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
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                            ),
                            placeholder = {
                                Text(text = "Nhóm truy cập", fontSize = 14.sp)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleAccountExpanded)
                                Icon(
                                    imageVector = if (roleAccountExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                        )
                        ExposedDropdownMenu(
                            expanded = roleAccountExpanded,
                            onDismissRequest = {
                                roleAccountExpanded = false
                            }
                        ) {
                            roleAccountList.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        roleAccountSelected = item
                                        updateProfileViewModel.uiState.value.roleaccountSelected =
                                            item
                                        roleAccountExpanded = false
                                        focusManager.clearFocus()
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    ButtonComponent(
                        onClick = {
                            if (updateProfileViewModel.uiState.value.fullnameEntered.isNotEmpty()) {
                                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                                val user = User(userID, updateProfileViewModel.uiState.value.usernameEntered,
                                    updateProfileViewModel.uiState.value.passwordEntered, statusAccountSelected,
                                    roleAccountSelected, Timestamp(df2.parse(lastLogin)), Timestamp(df2.parse(createdAt)), Timestamp(df2.parse(updatedAt)))
                                val employee = Employee(
                                    employeeID, userID, updateProfileViewModel.uiState.value.fullnameEntered, genderSelected, Timestamp(df1.parse(formattedDateOfBirth)),
                                    updateProfileViewModel.uiState.value.idcardEntered, updateProfileViewModel.uiState.value.placeofbirthEntered, updateProfileViewModel.uiState.value.placeofresidenceEntered, updateProfileViewModel.uiState.value.cityorprovinceEntered,
                                    updateProfileViewModel.uiState.value.districtEntered, updateProfileViewModel.uiState.value.wardorcommuneEntered, updateProfileViewModel.uiState.value.phonenumberEntered, updateProfileViewModel.uiState.value.emailaddressEntered,
                                    departmentSelected, positionSelected, Timestamp(df1.parse(formattedStartDate)), Timestamp(df1.parse(formattedEndDate)))

                                if (userID == "0") {
                                    user.userID = user.username
                                    employee.userID = user.username
                                    user.password = encodeToMD5(user.password)
                                    if (updateProfileViewModel.uiState.value.usernameEntered.isNotEmpty()
                                        && updateProfileViewModel.uiState.value.passwordEntered.isNotEmpty()) {
                                        val dbUser: CollectionReference = db.collection("User")
                                        val dbEmployee: CollectionReference = db.collection("Employee")
                                        var userList = mutableStateListOf<User?>()
                                        var employeeList = mutableStateListOf<Employee?>()
                                        var documentUserID = ""
                                        var documentEmployeeID = ""

                                        db.collection("User").get()
                                            .addOnSuccessListener {
                                                val UserList = it.documents
                                                for (d in UserList) {
                                                    val u: User? = d.toObject(User::class.java)
                                                    u?.userID = d.id
                                                    if (username.equals(u?.username)) {
                                                        userList.add(u)
                                                        break
                                                    }
                                                }
                                                if (userList.isEmpty()) {
                                                    dbUser.add(user)
                                                        .addOnSuccessListener {
                                                            dbEmployee.add(employee)
                                                            dbUser.get()
                                                                .addOnSuccessListener {
                                                                    val UserList = it.documents
                                                                    for (d in UserList) {
                                                                        val u: User? =
                                                                            d.toObject(User::class.java)
                                                                        u?.userID = d.id
                                                                        if (username.equals(u?.username)) {
                                                                            documentUserID = u?.userID.toString()
                                                                            userList.add(u)
                                                                            break
                                                                        }
                                                                    }
                                                                    if (userList.isNotEmpty()) {
                                                                        dbEmployee.get()
                                                                            .addOnSuccessListener {
                                                                                val EmployeeList = it.documents
                                                                                for (d in EmployeeList) {
                                                                                    val e: Employee? = d.toObject(Employee::class.java)
                                                                                    e?.employeeID = d.id
                                                                                    if (username.equals(e?.userID)) {
                                                                                        documentEmployeeID = e?.employeeID.toString()
                                                                                        employeeList.add(e)
                                                                                        break
                                                                                    }
                                                                                }
                                                                                if (employeeList.isNotEmpty()) {
                                                                                    user.userID = documentUserID
                                                                                    employee.userID = documentUserID
                                                                                    employee.employeeID = documentEmployeeID
                                                                                    db.collection("User")
                                                                                        .document(user.userID)
                                                                                        .set(user)
                                                                                    db.collection("Employee")
                                                                                        .document(employee.employeeID)
                                                                                        .set(employee)
                                                                                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                                                                                    backPreviousScreen()
                                                                                }
                                                                            }
                                                                    }
                                                                }
                                                        }
                                                        .addOnFailureListener {
                                                            Toast.makeText(context, "Thêm thất bại (Lỗi: $it)", Toast.LENGTH_SHORT).show()
                                                        }
                                                } else {
                                                    Toast.makeText(context, "Username đã tồn tại", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                    }
                                } else {
                                    db.collection("Employee").document(employeeID).set(employee)
                                        .addOnSuccessListener {
                                            db.collection("User").document(userID).set(user)
                                                .addOnSuccessListener {
                                                    Toast.makeText(
                                                        context,
                                                        "Cập nhật thành công",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    backPreviousScreen()
                                                }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                "Cập nhật thất bại",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
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
                        textValue = if (userID == "0") "Thêm" else "Cập nhật",
                        textSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun UpdateProfileScreenPreview() {
    UpdateProfileScreen(
        userID = "userID",
        role = "role",
        updateProfileViewModel = UpdateProfileViewModel(),
        backPreviousScreen = {}
    )
}
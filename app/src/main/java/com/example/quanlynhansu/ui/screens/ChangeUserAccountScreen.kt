package com.example.quanlynhansu.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlynhansu.firebase.encodeToMD5
import com.example.quanlynhansu.firebase.getAllUser
import com.example.quanlynhansu.models.User
import com.example.quanlynhansu.ui.AlertDialogComponent
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeUserAccountScreen(
    backInfoScreen: () -> Unit
) {
    val context = LocalContext.current

    val currentDateTime: LocalDateTime = LocalDateTime.now()
    val formattedCurrentDateTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm")
                .format(currentDateTime)
        }
    }

    var userList by remember {
        mutableStateOf<List<User>>(emptyList())
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            userList = getAllUser()
        }
    }

    var selectedUserID by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFD6229),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Quản lý tài khoản", fontSize = 18.sp, color = Color.White)
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
        var openChangeAlertDialog by remember {
            mutableStateOf(false)
        }

        when {
            openChangeAlertDialog -> {
                AlertDialogComponent(
                    onDismissRequest = {
                        openChangeAlertDialog = false
                        selectedUserID = ""
                    },
                    onConfirmation = {
                        openChangeAlertDialog = false
                        val updatedUser = User(selectedUserID, "", "", "", "", "", "", "")
                        val userList = mutableStateListOf<User?>()
                        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                        db.collection("User").get()
                            .addOnSuccessListener {
                                if (!it.isEmpty) {
                                    val UserList = it.documents
                                    for (d in UserList) {
                                        val u: User? = d.toObject(User::class.java)
                                        u?.userID = d.id
                                        if (updatedUser.userID == u?.userID) {
                                            updatedUser.userID = u?.userID.toString()
                                            updatedUser.username = u?.username.toString()
                                            updatedUser.password = encodeToMD5("123456")
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
                                        Toast.makeText(context, "Khôi phục về mật khẩu mặc định thành công", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            .addOnFailureListener {}
                        selectedUserID = ""
                    },
                    dialogTitle = "Bạn chắc chắn muốn khôi phục về mật khẩu mặc định hay không?",
                    dialogText = "Nếu nhấn vào OK, bạn sẽ không thể hoàn tác thao tác này được",
                    icon = Icons.Default.Warning
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "QUẢN LÝ TÀI KHOẢN",
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
                        items = userList,
                        itemContent = { _, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .wrapContentHeight()
                                    .aspectRatio(3f),
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
                                                    imageVector = Icons.Default.Person,
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
                                                    text = item.username,
                                                    fontSize = 16.sp,
                                                    color = Color(0xffea9010),
                                                    fontWeight = FontWeight.W500,
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = item.status,
                                                    fontSize = 14.sp,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.W300,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }

                                        IconButton(
                                            onClick = {
                                                selectedUserID = item.userID
                                                openChangeAlertDialog = !openChangeAlertDialog
                                            },
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = Color.Transparent
                                            )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = null,
                                                modifier = Modifier.size(24.dp),
                                                tint = Color.LightGray
                                            )
                                        }
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ChangeUserAccountScreenPreview() {
    ChangeUserAccountScreen(
        backInfoScreen = {}
    )
}
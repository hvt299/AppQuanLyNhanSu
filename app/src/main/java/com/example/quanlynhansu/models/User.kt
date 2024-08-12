package com.example.quanlynhansu.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class User(
    @Exclude var userID: String = "",
    var username: String = "",
    var password: String = "",
    var status: String = "",
    var role: String = "",
    var lastLogin: Timestamp = Timestamp.now(),
    var createdAt: Timestamp = Timestamp.now(),
    var updatedAt: Timestamp = Timestamp.now()
)
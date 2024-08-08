package com.example.quanlynhansu.models

import com.google.firebase.firestore.Exclude

data class User(
    @Exclude var userID: String = "",
    var username: String = "",
    var password: String = "",
    var status: String = "",
    var role: String = "",
    var lastLogin: String = "",
    var createdAt: String = "",
    var updatedAt: String = ""
)
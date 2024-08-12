package com.example.quanlynhansu.models

import com.google.firebase.firestore.Exclude

data class CheckTime(
    @Exclude var employeeID: String,
    var checkTime: String,
    var checkType: String
)

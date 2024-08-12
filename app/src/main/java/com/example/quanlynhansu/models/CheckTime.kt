package com.example.quanlynhansu.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class CheckTime(
    @Exclude var employeeID: String = "",
    var checkTime: Timestamp = Timestamp.now(),
    var checkType: String = ""
)

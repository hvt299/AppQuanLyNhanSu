package com.example.quanlynhansu.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Salary(
    @Exclude var employeeID: String = "",
    var fromDate: Timestamp = Timestamp.now(),
    var toDate: Timestamp = Timestamp.now(),
    var salary: Int = 0
)
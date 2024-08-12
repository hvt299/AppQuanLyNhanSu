package com.example.quanlynhansu.models

import com.google.firebase.firestore.Exclude

data class Salary(
    @Exclude var employeeID: String,
    var fromDate: String,
    var toDate: String,
    var salary: Int
)
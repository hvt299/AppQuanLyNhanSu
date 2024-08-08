package com.example.quanlynhansu.models

import com.google.firebase.firestore.Exclude

data class Employee(
    @Exclude var employeeID: String = "",
    var userID: String = "",
    var fullname: String = "",
    var gender: String? = "",
    var dateOfBirth: String = "",
    var idCard: String? = "",
    var placeOfBirth: String? = "",
    var placeOfResidence: String? = "",
    var cityOrProvince: String? = "",
    var district: String? = "",
    var wardOrCommune: String? = "",
    var phoneNumber: String? = "",
    var emailAddress: String? = "",
    var department: String? = "",
    var position: String = "",
    var startDate: String = "",
    var endDate: String = ""
)
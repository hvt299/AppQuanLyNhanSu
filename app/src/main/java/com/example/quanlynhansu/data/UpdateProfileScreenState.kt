package com.example.quanlynhansu.data

import java.time.LocalDate

data class UpdateProfileScreenState(
    var fullnameEntered: String = "",
    var genderSelected: String = "",
    var dateofbirthSelected: LocalDate = LocalDate.now(),
    var idcardEntered: String = "",
    var placeofbirthEntered: String = "",
    var placeofresidenceEntered: String = "",
    var cityorprovinceEntered: String = "",
    var districtEntered: String = "",
    var wardorcommuneEntered: String = "",
    var phonenumberEntered: String = "",
    var emailaddressEntered: String = "",

    // admin
    var departmentSelected: String = "",
    var positionSelected: String = "",
    var startdateSelected: LocalDate = LocalDate.now(),
    var enddateSelected: LocalDate = LocalDate.now(),
    var usernameEntered: String = "",
    var passwordEntered: String = "",
    var statusaccountSelected: String = "",
    var roleaccountSelected: String = ""
)

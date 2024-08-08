package com.example.quanlynhansu.data

import java.time.LocalDate

sealed class UpdateProfileDataUiEvents {
    data class FullnameEntered(val fullname: String) : UpdateProfileDataUiEvents()
    data class GenderSelected(val gender: String) : UpdateProfileDataUiEvents()
    data class DateOfBirthSelected(val dob: LocalDate) : UpdateProfileDataUiEvents()
    data class IDCardEntered(val idcard: String) : UpdateProfileDataUiEvents()
    data class PlaceOfBirthEntered(val pob: String) : UpdateProfileDataUiEvents()
    data class PlaceOfResidenceEntered(val por: String) : UpdateProfileDataUiEvents()
    data class CityOrProvinceEntered(val cop: String) : UpdateProfileDataUiEvents()
    data class DistrictEntered(val district: String) : UpdateProfileDataUiEvents()
    data class WardOrCommuneEntered(val woc: String) : UpdateProfileDataUiEvents()
    data class PhoneNumberEntered(val phone: String) : UpdateProfileDataUiEvents()
    data class EmailAddressEntered(val email: String) : UpdateProfileDataUiEvents()

    // admin
    data class DepartmentSelected(val department: String) : UpdateProfileDataUiEvents()
    data class PositionSelected(val position: String) : UpdateProfileDataUiEvents()
    data class StartDateSelected(val startdate: LocalDate) : UpdateProfileDataUiEvents()
    data class EndDateSelected(val enddate: LocalDate) : UpdateProfileDataUiEvents()
    data class UsernameEntered(val username: String) : UpdateProfileDataUiEvents()
    data class PasswordEntered(val password: String) : UpdateProfileDataUiEvents()
    data class StatusAccountSelected(val statusaccount: String) : UpdateProfileDataUiEvents()
    data class RoleAccountSelected(val roleaccount: String) : UpdateProfileDataUiEvents()
}
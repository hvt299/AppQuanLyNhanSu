package com.example.quanlynhansu.data

sealed class RegisterDataUiEvents {
    data class FullnameEntered(val fullname: String) : RegisterDataUiEvents()
    data class UsernameEntered(val username: String) : RegisterDataUiEvents()
    data class PasswordEntered(val password: String) : RegisterDataUiEvents()
    data class RePasswordEntered(val repassword: String) : RegisterDataUiEvents()
}
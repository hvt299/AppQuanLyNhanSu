package com.example.quanlynhansu.data

sealed class LoginDataUiEvents {
    data class UsernameEntered(val username: String) : LoginDataUiEvents()
    data class PasswordEntered(val password: String) : LoginDataUiEvents()
}
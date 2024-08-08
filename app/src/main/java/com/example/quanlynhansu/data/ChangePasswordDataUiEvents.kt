package com.example.quanlynhansu.data

sealed class ChangePasswordDataUiEvents {
    data class OldPasswordEntered(val oldpassword: String) : ChangePasswordDataUiEvents()
    data class NewPasswordEntered(val newpassword: String) : ChangePasswordDataUiEvents()
    data class ReNewPasswordEntered(val renewpassword: String) : ChangePasswordDataUiEvents()
}
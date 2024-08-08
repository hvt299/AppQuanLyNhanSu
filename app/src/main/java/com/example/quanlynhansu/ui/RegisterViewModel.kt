package com.example.quanlynhansu.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quanlynhansu.data.RegisterDataUiEvents
import com.example.quanlynhansu.data.RegisterScreenState

class RegisterViewModel : ViewModel() {
    var uiState = mutableStateOf(RegisterScreenState())

    fun onEvent(event: RegisterDataUiEvents) {
        when(event) {
            is RegisterDataUiEvents.FullnameEntered -> {
                uiState.value = uiState.value.copy(
                    fullnameEntered = event.fullname
                )
            }

            is RegisterDataUiEvents.UsernameEntered -> {
                uiState.value = uiState.value.copy(
                    usernameEntered = event.username
                )
            }

            is RegisterDataUiEvents.PasswordEntered -> {
                uiState.value = uiState.value.copy(
                    passwordEntered = event.password
                )
            }

            is RegisterDataUiEvents.RePasswordEntered -> {
                uiState.value = uiState.value.copy(
                    repasswordEntered = event.repassword
                )
            }
        }
    }
}
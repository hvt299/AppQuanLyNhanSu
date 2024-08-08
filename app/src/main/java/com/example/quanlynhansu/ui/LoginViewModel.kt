package com.example.quanlynhansu.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quanlynhansu.data.LoginDataUiEvents
import com.example.quanlynhansu.data.LoginScreenState

class LoginViewModel : ViewModel() {
    var uiState = mutableStateOf(LoginScreenState())

    fun onEvent(event: LoginDataUiEvents) {
        when(event) {
            is LoginDataUiEvents.UsernameEntered -> {
                uiState.value = uiState.value.copy(
                    usernameEntered = event.username
                )
            }

            is LoginDataUiEvents.PasswordEntered -> {
                uiState.value = uiState.value.copy(
                    passwordEntered = event.password
                )
            }
        }
    }
}
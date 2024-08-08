package com.example.quanlynhansu.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quanlynhansu.data.ChangePasswordDataUiEvents
import com.example.quanlynhansu.data.ChangePasswordScreenState

class ChangePasswordViewModel : ViewModel() {
    var uiState = mutableStateOf(ChangePasswordScreenState())

    fun onEvent(event: ChangePasswordDataUiEvents) {
        when(event) {
            is ChangePasswordDataUiEvents.OldPasswordEntered -> {
                uiState.value = uiState.value.copy(
                    oldpasswordEntered = event.oldpassword
                )
            }

            is ChangePasswordDataUiEvents.NewPasswordEntered -> {
                uiState.value = uiState.value.copy(
                    newpasswordEntered = event.newpassword
                )
            }

            is ChangePasswordDataUiEvents.ReNewPasswordEntered -> {
                uiState.value = uiState.value.copy(
                    renewpasswordEntered = event.renewpassword
                )
            }
        }
    }
}
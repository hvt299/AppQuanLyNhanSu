package com.example.quanlynhansu.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quanlynhansu.data.UpdateProfileDataUiEvents
import com.example.quanlynhansu.data.UpdateProfileScreenState

class UpdateProfileViewModel : ViewModel() {
    var uiState = mutableStateOf(UpdateProfileScreenState())

    fun onEvent(event: UpdateProfileDataUiEvents) {
        when(event) {
            is UpdateProfileDataUiEvents.FullnameEntered -> {
                uiState.value = uiState.value.copy(
                    fullnameEntered = event.fullname
                )
            }

            is UpdateProfileDataUiEvents.GenderSelected -> {
                uiState.value = uiState.value.copy(
                    genderSelected = event.gender
                )
            }

            is UpdateProfileDataUiEvents.DateOfBirthSelected -> {
                uiState.value = uiState.value.copy(
                    dateofbirthSelected = event.dob
                )
            }

            is UpdateProfileDataUiEvents.IDCardEntered -> {
                uiState.value = uiState.value.copy(
                    idcardEntered = event.idcard
                )
            }

            is UpdateProfileDataUiEvents.PlaceOfBirthEntered -> {
                uiState.value = uiState.value.copy(
                    placeofbirthEntered = event.pob
                )
            }

            is UpdateProfileDataUiEvents.PlaceOfResidenceEntered -> {
                uiState.value = uiState.value.copy(
                    placeofresidenceEntered = event.por
                )
            }

            is UpdateProfileDataUiEvents.CityOrProvinceEntered -> {
                uiState.value = uiState.value.copy(
                    cityorprovinceEntered = event.cop
                )
            }

            is UpdateProfileDataUiEvents.DistrictEntered -> {
                uiState.value = uiState.value.copy(
                    districtEntered = event.district
                )
            }

            is UpdateProfileDataUiEvents.WardOrCommuneEntered -> {
                uiState.value = uiState.value.copy(
                    wardorcommuneEntered = event.woc
                )
            }

            is UpdateProfileDataUiEvents.PhoneNumberEntered -> {
                uiState.value = uiState.value.copy(
                    phonenumberEntered = event.phone
                )
            }

            is UpdateProfileDataUiEvents.EmailAddressEntered -> {
                uiState.value = uiState.value.copy(
                    emailaddressEntered = event.email
                )
            }

            // admin
            is UpdateProfileDataUiEvents.DepartmentSelected -> {
                uiState.value = uiState.value.copy(
                    departmentSelected = event.department
                )
            }

            is UpdateProfileDataUiEvents.PositionSelected -> {
                uiState.value = uiState.value.copy(
                    positionSelected = event.position
                )
            }

            is UpdateProfileDataUiEvents.StartDateSelected -> {
                uiState.value = uiState.value.copy(
                    startdateSelected = event.startdate
                )
            }

            is UpdateProfileDataUiEvents.EndDateSelected -> {
                uiState.value = uiState.value.copy(
                    enddateSelected = event.enddate
                )
            }

            is UpdateProfileDataUiEvents.UsernameEntered -> {
                uiState.value = uiState.value.copy(
                    usernameEntered = event.username
                )
            }

            is UpdateProfileDataUiEvents.PasswordEntered -> {
                uiState.value = uiState.value.copy(
                    passwordEntered = event.password
                )
            }

            is UpdateProfileDataUiEvents.StatusAccountSelected -> {
                uiState.value = uiState.value.copy(
                    statusaccountSelected = event.statusaccount
                )
            }

            is UpdateProfileDataUiEvents.RoleAccountSelected -> {
                uiState.value = uiState.value.copy(
                    roleaccountSelected = event.roleaccount
                )
            }
        }
    }
}
package com.aldiprahasta.storyapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.domain.model.RegisterDomainModel
import com.aldiprahasta.storyapp.domain.usecase.RegisterUser
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class RegisterViewModel(registerUser: RegisterUser) : ViewModel() {
    private val registerUserRequest = MutableSharedFlow<RegisterRequestModel>(replay = 1)
    fun setRegisterUserRequest(registerRequestModel: RegisterRequestModel) {
        registerUserRequest.tryEmit(registerRequestModel)
    }

    private val nameField = MutableStateFlow("")
    fun setNameField(name: String) {
        nameField.value = name
    }

    private val emailField = MutableStateFlow("")
    fun setEmailField(email: String) {
        emailField.value = email
    }

    private val passwordField = MutableStateFlow("")
    fun setPasswordField(password: String) {
        passwordField.value = password
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val registerUser: SharedFlow<UiState<RegisterDomainModel>> = registerUserRequest.flatMapLatest {
        registerUser(it)
    }.shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            1
    )

    val isValidButton: StateFlow<Boolean> = combine(nameField, emailField, passwordField) { name, email, password ->
        name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && password.length >= 8
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
    )
}
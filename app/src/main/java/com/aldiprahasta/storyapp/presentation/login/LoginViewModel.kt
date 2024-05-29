package com.aldiprahasta.storyapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldiprahasta.storyapp.data.source.network.request.LoginRequestModel
import com.aldiprahasta.storyapp.domain.model.LoginDomainModel
import com.aldiprahasta.storyapp.domain.usecase.LoginUser
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

class LoginViewModel(loginUser: LoginUser) : ViewModel() {
    private val emailField = MutableStateFlow("")
    fun setEmailField(email: String) {
        this.emailField.value = email
    }

    private val passwordField = MutableStateFlow("")
    fun setPasswordField(password: String) {
        this.passwordField.value = password
    }

    private val loginRequestModel = MutableSharedFlow<LoginRequestModel>(1)
    fun setLoginRequestModel(loginRequestModel: LoginRequestModel) {
        this.loginRequestModel.tryEmit(loginRequestModel)
    }

    val isValidButton: StateFlow<Boolean> = combine(emailField, passwordField) { email, password ->
        email.isNotBlank() && password.isNotBlank() && password.length >= 8
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val loginUser: SharedFlow<UiState<LoginDomainModel>> = loginRequestModel.flatMapLatest { requestModel ->
        loginUser(requestModel)
    }.shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            1
    )
}
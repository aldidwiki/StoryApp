package com.aldiprahasta.storyapp.utils

import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.domain.model.RegisterDomainModel

fun RegisterResponse.mapToDomainModel(): RegisterDomainModel = RegisterDomainModel(
    isError = error ?: false,
    message = message ?: ""
)
package com.aldiprahasta.storyapp.utils

import com.aldiprahasta.storyapp.data.response.LoginResponse
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.data.response.StoryResponse
import com.aldiprahasta.storyapp.domain.model.LoginDomainModel
import com.aldiprahasta.storyapp.domain.model.RegisterDomainModel
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel

fun RegisterResponse.mapToDomainModel(): RegisterDomainModel = RegisterDomainModel(
        isError = error ?: false,
        message = message ?: ""
)

fun LoginResponse.mapToDomainModel(): LoginDomainModel = LoginDomainModel(
        isError = error ?: false,
        name = loginResponseModel?.name ?: "",
        token = loginResponseModel?.token ?: ""
)

fun StoryResponse.mapToDomainModelList(): List<StoryDomainModel> = listStory?.map { item ->
    StoryDomainModel(
            id = item.id,
            name = item.name ?: "",
            description = item.description ?: "",
            photoUrl = item.photoUrl ?: "",
            createdAt = item.createdAt ?: ""
    )
}?.sortedByDescending { it.createdAt } ?: emptyList()
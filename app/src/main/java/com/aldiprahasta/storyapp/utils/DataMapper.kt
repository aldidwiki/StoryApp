package com.aldiprahasta.storyapp.utils

import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity
import com.aldiprahasta.storyapp.data.source.network.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.source.network.response.LoginResponse
import com.aldiprahasta.storyapp.data.source.network.response.RegisterResponse
import com.aldiprahasta.storyapp.data.source.network.response.StoryResponse
import com.aldiprahasta.storyapp.domain.model.AddStoryDomainModel
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

fun StoryResponse.mapToEntityList(): List<StoryEntity> = listStory?.map { item ->
    StoryEntity(
            id = item.id,
            name = item.name ?: "",
            description = item.description ?: "",
            photoUrl = item.photoUrl ?: "",
            createdAt = item.createdAt ?: ""
    )
} ?: emptyList()

fun StoryEntity.mapToDomainModel(): StoryDomainModel = StoryDomainModel(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        createdAt = createdAt
)


fun AddStoryResponse.mapToDomainModel(): AddStoryDomainModel = AddStoryDomainModel(
        isError = error ?: false,
        message = message ?: ""
)
package com.aldiprahasta.storyapp.data

import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.Flow

class StoryRepositoryImpl(private val remoteDataSource: RemoteDataSource) : StoryRepository {
    override fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>> {
        return remoteDataSource.registerUser(registerRequestModel)
    }
}
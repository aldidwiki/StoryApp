package com.aldiprahasta.storyapp.data

import com.aldiprahasta.storyapp.data.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.response.LoginResponse
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.data.response.StoryResponse
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepositoryImpl(private val remoteDataSource: RemoteDataSource) : StoryRepository {
    override fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>> {
        return remoteDataSource.registerUser(registerRequestModel)
    }

    override fun loginUser(loginRequestModel: LoginRequestModel): Flow<UiState<LoginResponse>> {
        return remoteDataSource.loginUser(loginRequestModel)
    }

    override fun getStories(): Flow<UiState<StoryResponse>> {
        return remoteDataSource.getStories()
    }

    override fun addStory(imageFile: MultipartBody.Part, description: RequestBody): Flow<UiState<AddStoryResponse>> {
        return remoteDataSource.addStory(imageFile, description)
    }
}
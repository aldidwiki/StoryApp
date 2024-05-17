package com.aldiprahasta.storyapp.domain

import com.aldiprahasta.storyapp.data.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.response.LoginResponse
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.data.response.StoryResponse
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryRepository {
    fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>>
    fun loginUser(loginRequestModel: LoginRequestModel): Flow<UiState<LoginResponse>>
    fun getStories(): Flow<UiState<StoryResponse>>
    fun addStory(imageFile: MultipartBody.Part, description: RequestBody): Flow<UiState<AddStoryResponse>>
}
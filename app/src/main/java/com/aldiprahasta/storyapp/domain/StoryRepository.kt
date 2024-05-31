package com.aldiprahasta.storyapp.domain

import androidx.paging.PagingData
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity
import com.aldiprahasta.storyapp.data.source.network.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.source.network.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.source.network.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.source.network.response.LoginResponse
import com.aldiprahasta.storyapp.data.source.network.response.RegisterResponse
import com.aldiprahasta.storyapp.data.source.network.response.StoryResponse
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryRepository {
    fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>>

    fun loginUser(loginRequestModel: LoginRequestModel): Flow<UiState<LoginResponse>>

    fun getStories(): Flow<UiState<StoryResponse>>
    fun getStoriesWithPaging(): Flow<PagingData<StoryEntity>>

    fun addStory(imageFile: MultipartBody.Part, description: RequestBody): Flow<UiState<AddStoryResponse>>
}
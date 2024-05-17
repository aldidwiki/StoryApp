package com.aldiprahasta.storyapp.domain

import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>>
}
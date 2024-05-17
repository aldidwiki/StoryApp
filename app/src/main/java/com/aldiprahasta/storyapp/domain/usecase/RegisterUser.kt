package com.aldiprahasta.storyapp.domain.usecase

import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.domain.model.RegisterDomainModel
import com.aldiprahasta.storyapp.utils.UiState
import com.aldiprahasta.storyapp.utils.mapToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RegisterUser(private val storyRepository: StoryRepository) {
    operator fun invoke(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterDomainModel>> =
        storyRepository.registerUser(registerRequestModel).map { state ->
            when (state) {
                is UiState.Loading -> UiState.Loading
                is UiState.Error -> UiState.Error(state.throwable, state.errorMessage)
                is UiState.Success -> UiState.Success(
                    state.data.mapToDomainModel()
                )
            }
        }
}
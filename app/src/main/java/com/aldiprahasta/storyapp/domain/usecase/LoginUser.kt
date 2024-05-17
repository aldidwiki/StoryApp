package com.aldiprahasta.storyapp.domain.usecase

import com.aldiprahasta.storyapp.data.request.LoginRequestModel
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.domain.model.LoginDomainModel
import com.aldiprahasta.storyapp.utils.UiState
import com.aldiprahasta.storyapp.utils.mapToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginUser(private val storyRepository: StoryRepository) {
    operator fun invoke(loginRequestModel: LoginRequestModel): Flow<UiState<LoginDomainModel>> =
            storyRepository.loginUser(loginRequestModel).map { state ->
                when (state) {
                    is UiState.Loading -> UiState.Loading
                    is UiState.Error -> UiState.Error(
                            state.throwable,
                            state.errorMessage
                    )

                    is UiState.Success -> UiState.Success(
                            state.data.mapToDomainModel()
                    )
                }
            }
}
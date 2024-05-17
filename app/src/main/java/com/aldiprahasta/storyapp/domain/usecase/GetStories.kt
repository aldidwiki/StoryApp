package com.aldiprahasta.storyapp.domain.usecase

import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.utils.UiState
import com.aldiprahasta.storyapp.utils.mapToDomainModelList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStories(private val storyRepository: StoryRepository) {
    operator fun invoke(): Flow<UiState<List<StoryDomainModel>>> = storyRepository.getStories()
            .map { state ->
                when (state) {
                    is UiState.Loading -> UiState.Loading
                    is UiState.Error -> UiState.Error(
                            state.throwable,
                            state.errorMessage
                    )

                    is UiState.Success -> UiState.Success(
                            state.data.mapToDomainModelList()
                    )
                }
            }
}
package com.aldiprahasta.storyapp.domain.usecase

import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.domain.model.AddStoryDomainModel
import com.aldiprahasta.storyapp.utils.UiState
import com.aldiprahasta.storyapp.utils.mapToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStory(private val storyRepository: StoryRepository) {
    operator fun invoke(imageFile: MultipartBody.Part, description: RequestBody):
            Flow<UiState<AddStoryDomainModel>> = storyRepository.addStory(imageFile, description)
            .map { state ->
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
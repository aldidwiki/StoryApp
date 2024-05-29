package com.aldiprahasta.storyapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.utils.mapToDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStoriesWithPaging(private val storyRepository: StoryRepository) {
    operator fun invoke(): Flow<PagingData<StoryDomainModel>> = storyRepository.getStoriesWithPaging()
            .map { pagingData ->
                pagingData.map { entity ->
                    entity.mapToDomainModel()
                }
            }
}
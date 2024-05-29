package com.aldiprahasta.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aldiprahasta.storyapp.data.source.local.LocalDatabase
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity
import com.aldiprahasta.storyapp.data.source.network.RemoteService
import com.aldiprahasta.storyapp.utils.mapToEntityList
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
        private val localDatabase: LocalDatabase,
        private val remoteService: RemoteService
) : RemoteMediator<Int, StoryEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, StoryEntity>): MediatorResult {
        val page = INITIAL_PAGE_INDEX

        return try {
            val responseData = remoteService.getStories(
                    page = page,
                    size = state.config.pageSize
            )

            if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()?.listStory.isNullOrEmpty()

                localDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localDatabase.localDao().deleteAllStory()
                    }

                    responseData.body()?.let {
                        localDatabase.localDao().insertStory(it.mapToEntityList())
                    }
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                throw HttpException(responseData)
            }
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
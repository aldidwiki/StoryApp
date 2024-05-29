package com.aldiprahasta.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aldiprahasta.storyapp.data.source.local.LocalDatabase
import com.aldiprahasta.storyapp.data.source.local.entity.RemoteKeys
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
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = remoteService.getStories(
                    page = page,
                    size = state.config.pageSize
            )

            if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()?.listStory.isNullOrEmpty()

                localDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localDatabase.remoteKeyDao().deleteRemoteKeys()
                        localDatabase.localDao().deleteAllStory()
                    }

                    responseData.body()?.let { storyResponse ->
                        val prevKey = if (page == 1) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val keys = storyResponse.listStory?.map { item ->
                            RemoteKeys(id = item.id, prevKey = prevKey, nextKey = nextKey)
                        } ?: emptyList()

                        localDatabase.remoteKeyDao().insertAll(keys)
                        localDatabase.localDao().insertStory(storyResponse.mapToEntityList())
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDatabase.remoteKeyDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDatabase.remoteKeyDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDatabase.remoteKeyDao().getRemoteKeysId(id)
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
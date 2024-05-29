package com.aldiprahasta.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldiprahasta.storyapp.data.source.local.LocalDatabase
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity
import com.aldiprahasta.storyapp.data.source.network.RemoteDataSource
import com.aldiprahasta.storyapp.data.source.network.RemoteService
import com.aldiprahasta.storyapp.data.source.network.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.source.network.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.source.network.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.source.network.response.LoginResponse
import com.aldiprahasta.storyapp.data.source.network.response.RegisterResponse
import com.aldiprahasta.storyapp.data.source.network.response.StoryResponse
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepositoryImpl(
        private val remoteDataSource: RemoteDataSource,
        private val remoteService: RemoteService,
        private val localDatabase: LocalDatabase
) : StoryRepository {
    override fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>> {
        return remoteDataSource.registerUser(registerRequestModel)
    }

    override fun loginUser(loginRequestModel: LoginRequestModel): Flow<UiState<LoginResponse>> {
        return remoteDataSource.loginUser(loginRequestModel)
    }

    override fun getStories(): Flow<UiState<StoryResponse>> {
        return remoteDataSource.getStories()
    }

    override fun addStory(imageFile: MultipartBody.Part, description: RequestBody): Flow<UiState<AddStoryResponse>> {
        return remoteDataSource.addStory(imageFile, description)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getStoriesWithPaging(): Flow<PagingData<StoryEntity>> {
        val pagingConfig = PagingConfig(
                pageSize = 5
        )

        return Pager(
                config = pagingConfig,
                remoteMediator = StoryRemoteMediator(localDatabase, remoteService),
                pagingSourceFactory = {
                    localDatabase.localDao().getAllStory()
                }
        ).flow
    }
}
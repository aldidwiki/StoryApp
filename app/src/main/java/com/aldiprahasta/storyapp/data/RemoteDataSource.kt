package com.aldiprahasta.storyapp.data

import com.aldiprahasta.storyapp.data.network.RemoteService
import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber

class RemoteDataSource(private val remoteService: RemoteService) {
    fun registerUser(registerRequestModel: RegisterRequestModel): Flow<UiState<RegisterResponse>> = flow {
        emit(UiState.Loading)
        val response = remoteService.registerUser(registerRequestModel)
        if (!response.isSuccessful) emit(UiState.Error(HttpException(response), response.message()))
        else response.body()?.let { registerResponse ->
            emit(UiState.Success(registerResponse))
        }
    }.catch { t ->
        Timber.e(t)
        emit(UiState.Error(t))
    }.flowOn(Dispatchers.IO)
}
package com.aldiprahasta.storyapp.data

import com.aldiprahasta.storyapp.data.network.RemoteService
import com.aldiprahasta.storyapp.data.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.LoginResponse
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.data.response.StoryResponse
import com.aldiprahasta.storyapp.utils.UiState
import com.google.gson.Gson
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
        if (!response.isSuccessful) throw HttpException(response)
        else response.body()?.let { registerResponse ->
            emit(UiState.Success(registerResponse))
        }
    }.catch { t ->
        Timber.e(t)
        if (t is HttpException) {
            val jsonInString = t.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            emit(UiState.Error(t, errorBody.message))
        } else {
            emit(UiState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    fun loginUser(loginRequestModel: LoginRequestModel): Flow<UiState<LoginResponse>> = flow {
        emit(UiState.Loading)
        val response = remoteService.loginUser(loginRequestModel)
        if (!response.isSuccessful) throw HttpException(response)
        else response.body()?.let { loginResponse ->
            emit(UiState.Success(loginResponse))
        }
    }.catch { t ->
        Timber.e(t)
        if (t is HttpException) {
            val jsonInString = t.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            emit(UiState.Error(t, errorBody.message))
        } else {
            emit(UiState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    fun getStories(): Flow<UiState<StoryResponse>> = flow {
        emit(UiState.Loading)
        val response = remoteService.getStories()
        if (!response.isSuccessful) throw HttpException(response)
        else response.body()?.let { storyResponse ->
            emit(UiState.Success(storyResponse))
        }
    }.catch { t ->
        Timber.e(t)
        emit(UiState.Error(t))
    }.flowOn(Dispatchers.IO)
}
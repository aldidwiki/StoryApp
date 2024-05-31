package com.aldiprahasta.storyapp.data.source.network

import com.aldiprahasta.storyapp.data.source.network.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.source.network.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.source.network.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.source.network.response.LoginResponse
import com.aldiprahasta.storyapp.data.source.network.response.RegisterResponse
import com.aldiprahasta.storyapp.utils.UiState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    fun addStory(imageFile: MultipartBody.Part, description: RequestBody): Flow<UiState<AddStoryResponse>> =
            flow {
                emit(UiState.Loading)
                val response = remoteService.addStories(imageFile, description)
                if (!response.isSuccessful) throw HttpException(response)
                else response.body()?.let { addStoryResponse ->
                    emit(UiState.Success(addStoryResponse))
                }
            }.catch { t ->
                Timber.e(t)
                emit(UiState.Error(t))
            }.flowOn(Dispatchers.IO)
}
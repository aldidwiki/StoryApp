package com.aldiprahasta.storyapp.data.network

import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteService {
    @POST("register")
    suspend fun registerUser(@Body registerRequestModel: RegisterRequestModel): Response<RegisterResponse>
}
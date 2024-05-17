package com.aldiprahasta.storyapp.data.network

import com.aldiprahasta.storyapp.data.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.response.LoginResponse
import com.aldiprahasta.storyapp.data.response.RegisterResponse
import com.aldiprahasta.storyapp.data.response.StoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RemoteService {
    @POST("register")
    suspend fun registerUser(@Body registerRequestModel: RegisterRequestModel): Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body loginRequestModel: LoginRequestModel): Response<LoginResponse>

    @GET("stories")
    suspend fun getStories(): Response<StoryResponse>
}
package com.aldiprahasta.storyapp.data.source.network

import com.aldiprahasta.storyapp.data.source.network.request.LoginRequestModel
import com.aldiprahasta.storyapp.data.source.network.request.RegisterRequestModel
import com.aldiprahasta.storyapp.data.source.network.response.AddStoryResponse
import com.aldiprahasta.storyapp.data.source.network.response.LoginResponse
import com.aldiprahasta.storyapp.data.source.network.response.RegisterResponse
import com.aldiprahasta.storyapp.data.source.network.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RemoteService {
    @POST("register")
    suspend fun registerUser(@Body registerRequestModel: RegisterRequestModel): Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body loginRequestModel: LoginRequestModel): Response<LoginResponse>

    @GET("stories")
    suspend fun getStories(
            @Query("page") page: Int = 1,
            @Query("size") size: Int = 5
    ): Response<StoryResponse>

    @Multipart
    @POST("stories")
    suspend fun addStories(
            @Part file: MultipartBody.Part,
            @Part("description") description: RequestBody
    ): Response<AddStoryResponse>
}
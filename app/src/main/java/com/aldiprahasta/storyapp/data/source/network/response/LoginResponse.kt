package com.aldiprahasta.storyapp.data.source.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

        @field:SerializedName("loginResult")
        val loginResponseModel: LoginResponseModel?,

        @field:SerializedName("error")
        val error: Boolean?,

        @field:SerializedName("message")
        val message: String?
)

data class LoginResponseModel(

        @field:SerializedName("name")
        val name: String?,

        @field:SerializedName("userId")
        val userId: String?,

        @field:SerializedName("token")
        val token: String?
)

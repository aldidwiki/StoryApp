package com.aldiprahasta.storyapp.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestModel(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null
)

package com.aldiprahasta.storyapp.domain.model

data class LoginDomainModel(
        val isError: Boolean,
        val name: String,
        val token: String
)

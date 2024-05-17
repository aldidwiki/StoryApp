package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.presentation.addstory.AddStoryViewModel
import com.aldiprahasta.storyapp.presentation.home.HomeViewModel
import com.aldiprahasta.storyapp.presentation.login.LoginViewModel
import com.aldiprahasta.storyapp.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddStoryViewModel)
}
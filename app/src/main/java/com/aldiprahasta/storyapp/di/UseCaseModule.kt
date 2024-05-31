package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.domain.usecase.AddStory
import com.aldiprahasta.storyapp.domain.usecase.GetStoriesWithPaging
import com.aldiprahasta.storyapp.domain.usecase.LoginUser
import com.aldiprahasta.storyapp.domain.usecase.RegisterUser
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::RegisterUser)
    factoryOf(::LoginUser)
    factoryOf(::AddStory)
    factoryOf(::GetStoriesWithPaging)
}
package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.domain.usecase.AddStory
import com.aldiprahasta.storyapp.domain.usecase.GetStories
import com.aldiprahasta.storyapp.domain.usecase.GetStoriesWithPaging
import com.aldiprahasta.storyapp.domain.usecase.LoginUser
import com.aldiprahasta.storyapp.domain.usecase.RegisterUser
import com.aldiprahasta.storyapp.domain.usecase.wrapper.GetStoryWrapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::RegisterUser)
    factoryOf(::LoginUser)
    factoryOf(::GetStories)
    factoryOf(::AddStory)
    factoryOf(::GetStoriesWithPaging)
}

val useCaseWrapperModule = module {
    factoryOf(::GetStoryWrapper)
}
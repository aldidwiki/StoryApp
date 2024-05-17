package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.domain.usecase.GetStories
import com.aldiprahasta.storyapp.domain.usecase.LoginUser
import com.aldiprahasta.storyapp.domain.usecase.RegisterUser
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::RegisterUser)
    factoryOf(::LoginUser)
    factoryOf(::GetStories)
}
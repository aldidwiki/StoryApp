package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.domain.usecase.RegisterUser
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::RegisterUser)
}
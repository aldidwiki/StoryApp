package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.data.StoryRepositoryImpl
import com.aldiprahasta.storyapp.domain.StoryRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::StoryRepositoryImpl) { bind<StoryRepository>() }
}
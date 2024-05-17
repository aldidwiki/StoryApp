package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.utils.MyPreferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::MyPreferences)
}
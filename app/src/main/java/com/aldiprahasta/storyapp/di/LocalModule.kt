package com.aldiprahasta.storyapp.di

import android.content.Context
import androidx.room.Room
import com.aldiprahasta.storyapp.data.source.local.LocalDao
import com.aldiprahasta.storyapp.data.source.local.LocalDatabase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    singleOf(::provideLocalDatabase)
    singleOf(::provideLocalDao) { bind<LocalDao>() }
}

private fun provideLocalDatabase(context: Context): LocalDatabase {
    return Room.databaseBuilder(
            context = context,
            klass = LocalDatabase::class.java,
            name = LocalDatabase.DB_NAME
    ).build()
}

private fun provideLocalDao(localDatabase: LocalDatabase): LocalDao = localDatabase.localDao()
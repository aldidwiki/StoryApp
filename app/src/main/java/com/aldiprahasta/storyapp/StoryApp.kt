package com.aldiprahasta.storyapp

import android.app.Application
import com.aldiprahasta.storyapp.di.appModule
import com.aldiprahasta.storyapp.di.localModule
import com.aldiprahasta.storyapp.di.remoteModule
import com.aldiprahasta.storyapp.di.repositoryModule
import com.aldiprahasta.storyapp.di.useCaseModule
import com.aldiprahasta.storyapp.di.useCaseWrapperModule
import com.aldiprahasta.storyapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class StoryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StoryApp)
            modules(
                    localModule,
                    remoteModule,
                    repositoryModule,
                    useCaseModule,
                    useCaseWrapperModule,
                    viewModelModule,
                    appModule
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
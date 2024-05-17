package com.aldiprahasta.storyapp.di

import com.aldiprahasta.storyapp.data.RemoteDataSource
import com.aldiprahasta.storyapp.data.network.RemoteService
import com.aldiprahasta.storyapp.utils.MyPreferences
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    singleOf(::provideRetrofit)
    singleOf(::provideRemoteService) { bind<RemoteService>() }
    singleOf(::RemoteDataSource)
}

private fun provideRetrofit(myPreferences: MyPreferences): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val okhttp = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val token = runBlocking { myPreferences.getTokenFromDataStore() }
            val requestHeaders = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        .build()

    return Retrofit.Builder()
        .baseUrl("https://story-api.dicoding.dev/v1/")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(okhttp)
        .build()
}

private fun provideRemoteService(retrofit: Retrofit): RemoteService {
    return retrofit.create(RemoteService::class.java)
}
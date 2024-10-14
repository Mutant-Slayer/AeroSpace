package com.example.aerospace.di.module

import android.content.Context
import com.example.aerospace.AeroSpaceApplication
import com.example.aerospace.data.api.NetworkService
import com.example.aerospace.di.ApplicationContext
import com.example.aerospace.di.BaseUrl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AeroSpaceApplication) {

    @ApplicationContext
    @Provides
    fun providesApplicationContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun providesBaseUrl(): String {
        return "https://api.spacexdata.com/v3/"
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesNetworkService(
        @BaseUrl url: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
}
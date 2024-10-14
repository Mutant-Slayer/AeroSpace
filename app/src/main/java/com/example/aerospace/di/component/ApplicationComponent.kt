package com.example.aerospace.di.component

import com.example.aerospace.AeroSpaceApplication
import com.example.aerospace.data.api.NetworkService
import com.example.aerospace.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: AeroSpaceApplication)

    fun provideNetworkService(): NetworkService
}
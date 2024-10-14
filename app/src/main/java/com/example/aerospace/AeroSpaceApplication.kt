package com.example.aerospace

import android.app.Application
import com.example.aerospace.di.component.ApplicationComponent
import com.example.aerospace.di.component.DaggerApplicationComponent
import com.example.aerospace.di.module.ApplicationModule

class AeroSpaceApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}
package com.example.aerospace.di.component

import com.example.aerospace.di.FragmentScope
import com.example.aerospace.di.module.FragmentModule
import com.example.aerospace.ui.satelliteList.SatelliteListFragment
import dagger.Component

@FragmentScope
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {

    fun inject(fragment: SatelliteListFragment)
}
package com.example.aerospace.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aerospace.data.model.SatelliteListResponseItem
import com.example.aerospace.data.repository.SatelliteListRepository
import com.example.aerospace.di.FragmentContext
import com.example.aerospace.ui.base.ViewModelProviderFactory
import com.example.aerospace.ui.satelliteList.SatelliteListAdapter
import com.example.aerospace.ui.satelliteList.SatelliteListViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

    @FragmentContext
    @Provides
    fun provideContext(): Fragment {
        return fragment
    }

    @Provides
    fun provideSatelliteListViewModel(satelliteListRepository: SatelliteListRepository): SatelliteListViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(SatelliteListViewModel::class) {
            SatelliteListViewModel(satelliteListRepository)
        })[SatelliteListViewModel::class.java]
    }

    @Provides
    fun provideSatelliteListAdapter(): SatelliteListAdapter {
        return SatelliteListAdapter(
            context = fragment.requireContext(),
            satelliteList = ArrayList()
        )
    }
}
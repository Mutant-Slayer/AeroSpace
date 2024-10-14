package com.example.aerospace.data.repository

import com.example.aerospace.data.api.NetworkService
import com.example.aerospace.data.model.SatelliteListResponseItem
import com.example.aerospace.di.FragmentScope
import javax.inject.Inject

@FragmentScope
class SatelliteListRepository @Inject constructor(private val networkService: NetworkService) {

    suspend fun getSatelliteList(): List<SatelliteListResponseItem> {
        return networkService.getSatelliteList()
    }
}
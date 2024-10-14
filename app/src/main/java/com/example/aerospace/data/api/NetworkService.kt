package com.example.aerospace.data.api

import com.example.aerospace.data.model.SatelliteListResponseItem
import retrofit2.http.GET

interface NetworkService {

    @GET("launches")
    suspend fun getSatelliteList(): List<SatelliteListResponseItem>
}
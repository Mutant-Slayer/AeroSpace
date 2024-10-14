package com.example.aerospace.ui.satelliteList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aerospace.data.model.SatelliteListResponseItem
import com.example.aerospace.data.repository.SatelliteListRepository
import com.example.aerospace.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class SatelliteListViewModel @Inject constructor(private val satelliteListRepository: SatelliteListRepository) :
    ViewModel() {

    private val _satelliteList = MutableLiveData<UiState<List<SatelliteListResponseItem>>>()
    val satelliteList = _satelliteList

    init {
        getSatelliteList()
    }

    private fun getSatelliteList() {
        _satelliteList.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = satelliteListRepository.getSatelliteList()
                _satelliteList.value = UiState.Success(response)
            } catch (e: Exception) {
                _satelliteList.value = UiState.Error("Failed to fetch satellite list: ${e.message}")
            }
        }
    }
}
package com.konkuk.plzfarmer.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.plzfarmer.remote.repository.WeatherRepository
import com.konkuk.plzfarmer.remote.response.WeatherResponse
import com.konkuk.plzfarmer.utils.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel (val repository: WeatherRepository) : ViewModel() {

    private val _weatherResponse = MutableLiveData<ApiState<out WeatherResponse>>()
    val weatherResponse: LiveData<ApiState<out WeatherResponse>> = _weatherResponse

    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherResponse.postValue(repository.getCurrentWeather(lat, lon))
        }
    }
}
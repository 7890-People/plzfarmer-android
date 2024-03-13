package com.konkuk.plzfarmer.remote.repository

import com.konkuk.plzfarmer.presentation.base.BaseRepository
import com.konkuk.plzfarmer.remote.api.WeatherApi
import com.konkuk.plzfarmer.remote.response.WeatherResponse
import com.konkuk.plzfarmer.utils.network.ApiState
import com.konkuk.plzfarmer.utils.network.RetrofitClient

class WeatherRepository : BaseRepository(){
    private val apiKey: String = "6e1e22280563c5494c57fec8379cab3d"
    private val lang: String = "KR"

    val api = RetrofitClient.createWeather(WeatherApi::class.java)

    suspend fun getCurrentWeather(lat: Double, lon: Double) : ApiState<out WeatherResponse> = makeRequest {
        api.getCurrentWeatherList(lat, lon, apiKey, lang)
    }
}
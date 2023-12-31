package com.openweather.data.source

import com.openweather.data.entity.WeatherResponse
import retrofit2.Response

interface WeatherSource {
    suspend fun getWeather(
        lat: Double,
        long: Double,
    ):Response<WeatherResponse>

    suspend fun getWeather(
        q: String,
    ):Response<WeatherResponse>
}

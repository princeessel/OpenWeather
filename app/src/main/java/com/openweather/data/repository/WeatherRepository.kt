package com.openweather.data.repository

import com.openweather.data.source.WeatherSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherSource: WeatherSource
) {
    suspend fun getWeather(
        lat: Double,
        long: Double,
    ) = weatherSource.getWeather(lat, long)

    suspend fun getWeather(
        q: String,
    ) = weatherSource.getWeather(q)
}

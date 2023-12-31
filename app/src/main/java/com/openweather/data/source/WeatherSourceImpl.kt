package com.openweather.data.source

import com.openweather.data.entity.WeatherResponse
import com.openweather.data.service.WeatherService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class WeatherSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
    @Named("API_ID") private val appId: String,
    @Named("API_UNITS") private val units: String
): WeatherSource {
    override suspend fun getWeather(
        lat: Double,
        long: Double,
    ): Response<WeatherResponse> = weatherService.getWeather(lat, long, appId, units)

    override suspend fun getWeather(
        q: String,
    ): Response<WeatherResponse> = weatherService.getWeather(q, appId, units)
}

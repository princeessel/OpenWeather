package com.openweather.data.service

import com.openweather.data.entity.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService{

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String
    ):Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ):Response<WeatherResponse>
}

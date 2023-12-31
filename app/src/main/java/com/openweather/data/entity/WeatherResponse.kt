package com.openweather.data.entity

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    val coord: CoordEntity? = null,
    val weather: List<WeatherEntity>? = null,
    val base: String? = null,
    val main: MainEntity? = null,
    val visibility: Int? = null,
    val wind: WindEntity? = null,
    val clouds: CloudsEntity? = null,
    val dt: Int? = null,
    val sys: SysEntity? = null,
    val timezone: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
) {

    data class CoordEntity (
        val lat: Double? = null,
        val lon: Double? = null,
    )

    data class WeatherEntity (
        val id: Int? = null,
        val main: String? = null,
        val description: String? = null,
        val icon: String? = null,
    )

    data class MainEntity (
        val temp: Double? = null,
        @SerializedName("feels_like") val feelsLike: Double? = null,
        @SerializedName("temp_min") val tempMin: Double? = null,
        @SerializedName("temp_max") val tempMax: Double? = null,
        val pressure: Int? = null,
        val humidity: Int? = null,
    )

    data class WindEntity (
        val speed: Double? = null,
        val deg: Int? = null,
    )

    data class CloudsEntity (
        val all: Int? = null,
    )

    data class SysEntity (
        val type: Int? = null,
        val id: Int? = null,
        val country: String? = null,
        val sunrise: Int? = null,
        val sunset: Int? = null,
    )
}

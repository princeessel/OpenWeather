package com.openweather.domain.model

data class Weather (
    val coord: Coord?,
    val weather: List<Weather>?,
    val base: String?,
    val main: Main?,
    val visibility: Int?,
    val wind: Wind?,
    val clouds: Clouds?,
    val dt: Int?,
    val sys: Sys?,
    val timezone: Int?,
    val id: Int?,
    val name: String?,
    val cod: Int?,
) {
    data class Coord (
        val lat: Double?,
        val lon: Double?,
    )

    data class Weather (
        val id: Int?,
        val main: String?,
        val description: String?,
        val icon: String?,
    )

    data class Main (
        val temp: Double?,
        val feelsLike: Double?,
        val tempMin: Double?,
        val tempMax: Double?,
        val pressure: Int?,
        val humidity: Int?,
    )

    data class Wind (
        val speed: Double?,
        val deg: Int?,
    )

    data class Clouds (
        val all: Int?,
    )

    data class Sys (
        val type: Int?,
        val id: Int?,
        val country: String?,
        val sunrise: Int?,
        val sunset: Int?,
    )
}

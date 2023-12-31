package com.openweather.domain

import com.openweather.data.entity.WeatherResponse
import com.openweather.domain.model.Weather

fun WeatherResponse.toDomain() = Weather(
    coord = coord?.toDomain(),
    weather = weather?.map { it.toDomain() },
    base = base,
    main = main?.toDomain(),
    visibility = visibility,
    wind = wind?.toDomain(),
    clouds = clouds?.toDomain(),
    dt = dt,
    sys = sys?.toDomain(),
    timezone = timezone,
    id = id,
    name = name,
    cod = cod
)

fun WeatherResponse.WeatherEntity.toDomain() = Weather.Weather(
    id = id,
    main = main,
    description = description,
    icon = icon
)

fun WeatherResponse.CoordEntity.toDomain() = Weather.Coord(
    lon = lon,
    lat = lat
)

fun WeatherResponse.MainEntity.toDomain() = Weather.Main(
    temp = temp,
    feelsLike = feelsLike,
    tempMin = tempMin,
    tempMax = tempMax,
    pressure = pressure,
    humidity = humidity
)

fun WeatherResponse.WindEntity.toDomain() = Weather.Wind(
    speed = speed,
    deg = deg
)

fun WeatherResponse.CloudsEntity.toDomain() = Weather.Clouds(
    all = all
)

fun WeatherResponse.SysEntity.toDomain() = Weather.Sys(
    type = type,
    id = id,
    country = country,
    sunrise = sunrise,
    sunset = sunset
)

package com.openweather.domain.weather

import com.openweather.data.repository.WeatherRepository
import com.openweather.domain.model.Resource
import com.openweather.domain.model.Weather
import com.openweather.domain.toDomain
import javax.inject.Inject

class GetWeatherByQueryUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend fun execute(q: String): Resource<Weather> =
        weatherRepository.getWeather(q).let {
            if (it.isSuccessful){
                Resource.success(it.body()?.toDomain())
            } else {
                Resource.error()
            }
        }
}

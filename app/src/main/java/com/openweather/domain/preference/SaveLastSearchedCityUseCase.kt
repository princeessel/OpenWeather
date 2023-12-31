package com.openweather.domain.preference

import com.openweather.data.repository.PreferenceRepository
import com.openweather.domain.model.Resource
import javax.inject.Inject

class SaveLastSearchedCityUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    fun execute(city: String) = preferenceRepository.setLastSearchedCity(city)
}

package com.openweather.domain.preference

import com.openweather.data.repository.PreferenceRepository
import javax.inject.Inject

class GetLastSearchedCityUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    fun execute(): String = preferenceRepository.getLastSearchedCity()
}

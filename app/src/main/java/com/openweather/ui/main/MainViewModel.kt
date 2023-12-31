package com.openweather.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.openweather.Constants
import com.openweather.domain.model.Status
import com.openweather.domain.model.Weather
import com.openweather.domain.preference.GetLastSearchedCityUseCase
import com.openweather.domain.preference.SaveLastSearchedCityUseCase
import com.openweather.domain.weather.GetWeatherByLatLongUseCase
import com.openweather.domain.weather.GetWeatherByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherByLatLongUseCase: GetWeatherByLatLongUseCase,
    private val getWeatherByQueryUseCase: GetWeatherByQueryUseCase,
    private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
    private val saveLastSearchedCityUseCase: SaveLastSearchedCityUseCase,
):ViewModel(){

    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState>
        get() = _uiState

    private var isUserLocation: Boolean = false
    private var searchLocation: String? = null

    fun getDefaultWeather(context: Context) {
        getLastSearchedCityUseCase.execute().takeIf { it.isNotEmpty() }?.let {
            _uiState.postValue(
                _uiState.value?.copy(
                    searchLocation = it,
                    isUserLocation = false,
                )
            )
            getWeatherForLocation(it)
        } ?: getCurrentLocation(context)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
            val geocoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(it.latitude, it.longitude, 1) { addresses ->
                    addresses.firstOrNull()?.let { address ->
                        searchLocation = address.locality
                        isUserLocation = true
                    }
                }
            } else {
                geocoder.getFromLocation(it.latitude, it.longitude, 1)?.firstOrNull()?.let { address ->
                    searchLocation = address.locality
                    isUserLocation = true
                }
            }

            getWeatherForLocation(it.latitude, it.longitude)
        }
    }

    fun getWeatherForLocation(query: String)  = viewModelScope.launch {
        isUserLocation = false
        searchLocation = query
        _uiState.postValue(
            UiState(
                isLoading = true,
                searchLocation = searchLocation,
                isUserLocation = isUserLocation,
            )
        )
        getWeatherByQueryUseCase.execute(query).let {
            if (it.status == Status.SUCCESS) {
                saveLastSearchedCityUseCase.execute(query)
                showWeather(it.data)
            } else {
                showError()
            }
        }
    }

    private fun getWeatherForLocation(lat: Double, long: Double)  = viewModelScope.launch {
        _uiState.postValue(
            UiState(
                isLoading = true,
                searchLocation = searchLocation,
                isUserLocation = isUserLocation,
            )
        )
        getWeatherByLatLongUseCase.execute(lat, long).let {
            if (it.status == Status.SUCCESS) {
                showWeather(it.data)
            } else {
                showError()
            }
        }
    }

    private fun showWeather(weather: Weather?) {
        val icon = weather?.weather?.first()?.icon?.let { image ->
            "https://openweathermap.org/img/wn/${image}@2x.png"
        }

        _uiState.postValue(
            UiState(
                searchLocation = searchLocation,
                weather = weather,
                weatherIcon = icon,
            )
        )
    }

    private fun showError(error: String? = null) {
        _uiState.postValue(
            UiState(
                searchLocation = searchLocation,
                error = error ?: Constants.GENERIC_ERROR,
            )
        )
    }

    data class UiState (
        val isLoading: Boolean = false,
        val weather: Weather? = null,
        val weatherIcon: String? = null,
        val error: String? = null,
        val searchLocation: String? = null,
        val isUserLocation: Boolean = false,
    )
}

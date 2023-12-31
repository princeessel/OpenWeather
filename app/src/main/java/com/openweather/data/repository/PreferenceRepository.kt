package com.openweather.data.repository

import android.content.Context
import com.openweather.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getLastSearchedCity(): String {
        return context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE).getString(Constants.PREFS_KEY_LAST_SEARCHED_CITY, "") ?: ""
    }

    fun setLastSearchedCity(city: String) {
        context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE).edit().putString(Constants.PREFS_KEY_LAST_SEARCHED_CITY, city).apply()
    }
}

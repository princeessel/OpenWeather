package com.openweather.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.openweather.R
import com.openweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getCurrentLocation()
            } else -> { }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initViews()
        getCurrentLocation()
    }

    private fun initObservers() {
        mainViewModel.uiState.observe(this) {
            if (it.isLoading) {
                binding.submitButton.isEnabled = false
                binding.temp.text = ""
                binding.tempDescription.text = ""
                binding.tempRange.text = ""
                binding.weatherIcon.isVisible = false

                return@observe
            }

            binding.submitButton.isEnabled = true

            it.searchLocation?.let { location ->
                binding.locationInput.editText?.setText(location)
            }

            it.weather?.let { weather ->
                binding.temp.text = resources.getString(R.string.temperature, weather.main?.temp.toString())
                binding.tempDescription.text = weather.weather?.first()?.description?.uppercase(Locale.ROOT)
                binding.tempRange.text = resources.getString(R.string.temp_range, weather.main?.tempMax.toString(), weather.main?.tempMin.toString())
            }

            it.weatherIcon?.let { image ->
                binding.weatherIcon.isVisible = true
                Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .into(binding.weatherIcon)
            } ?: {
                binding.weatherIcon.isVisible = false
            }

            it.error?.let { error ->
                binding.temp.text = ""
                binding.tempDescription.text = ""
                binding.tempRange.text = ""
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews() {
        binding.submitButton.setOnClickListener {
            val query = binding.locationInput.editText?.text.toString()
            mainViewModel.getWeatherForLocation(query)
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }
        mainViewModel.getDefaultWeather(this@MainActivity)
    }
}

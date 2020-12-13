package com.example.weatherapp.today

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.network.WeatherModel
import kotlinx.coroutines.launch

import java.lang.Exception

class TodayViewModel : ViewModel() {

    private val _currentLocation = MutableLiveData<Location>()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _weather = MutableLiveData<WeatherModel>()
    val weather: LiveData<WeatherModel>
        get() = _weather

    init {

    }

    fun onLocationUpdate(location: Location) {
        _currentLocation.value = location
        getWeatherDataForCurrentLocation()
    }

    private fun getWeatherDataForCurrentLocation() {
        _currentLocation.value?.let { it ->
            getWeatherData(it.latitude, it.longitude)
        }
    }

    private fun getWeatherData(lat: Double, lon: Double) {

        viewModelScope.launch {
            try {
                val result = WeatherApi.retrofitService.getCurrentWeatherData(lat,lon, UNIT,API_KEY)
                _weather.value = result
            } catch (e: Exception) {
                _status.value = e.message
            }
        }
    }

    companion object {
        private const val API_KEY = "023f4cdd78b9af018566e86ab129fcd5"
        private const val UNIT = "metric"
    }
}

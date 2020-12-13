package com.example.weatherapp.daily

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.Daily
import com.example.weatherapp.network.DailyWeatherModel
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class WeatherApiStatus { START, LOADING, ERROR, DONE }

class DailyViewModel : ViewModel() {

    private val _currentLocation = MutableLiveData<Location>()

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus>
        get() = _status

    private val _dailyWeather = MutableLiveData<List<Daily>>()
    val dailyWeather: LiveData<List<Daily>>
        get() = _dailyWeather

    init {
        _status.value = WeatherApiStatus.START
        getDailyWeatherData()
    }

    fun onLocationUpdate(location: Location) {
        _currentLocation.value = location
        getLocationDailyWeatherData()
    }

    private fun getLocationDailyWeatherData() {
        _currentLocation.value?.let {
            getDailyWeatherData()
        }
    }

    private fun getDailyWeatherData() {
        viewModelScope.launch {
            try {
                val result =
                    WeatherApi.retrofitService.getDailyWeatherData(35.1, 139.0, EXCLUDE, UNIT, API_KEY)
                _status.value = WeatherApiStatus.LOADING
                Log.d(TAG, "getDailyWeatherData: ${result.daily.size}")
                if (result.daily.isNotEmpty()) {
                    _status.value = WeatherApiStatus.DONE
                    _dailyWeather.value = result.daily
                }
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
                Log.d(TAG, "getDailyWeatherData: Error:  ${e.message}")
                _dailyWeather.value = ArrayList()
            }

        }
    }


    companion object {
        private const val API_KEY = "023f4cdd78b9af018566e86ab129fcd5"
        private const val UNIT = "metric"
        private const val EXCLUDE = "current,hourly,minutely"

        private const val TAG = "DailyViewModel"
    }
}
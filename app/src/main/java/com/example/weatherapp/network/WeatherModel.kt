package com.example.weatherapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherModel(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Float
) : Parcelable

@Parcelize
data class Coord(
    val lon: Double,
    val lat: Double
) : Parcelable

@Parcelize
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable

@Parcelize
data class Main(
    val temp: Double,
    val feels_like: Float,
    val temp_min:Float,
    val temp_max:Float,
    val pressure: Float,
    val humidity: Float
) : Parcelable

@Parcelize
data class Wind(
    val speed: Float,
    val deg: Float,
) : Parcelable

@Parcelize
data class Clouds(
    val all: Int
) : Parcelable

@Parcelize
data class Sys(
    val type: Int,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
) : Parcelable
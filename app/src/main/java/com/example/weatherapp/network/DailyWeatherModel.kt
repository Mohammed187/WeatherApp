package com.example.weatherapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DailyWeatherModel(
    val lat:Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset:Int,
    val daily:List<Daily>
) : Parcelable

@Parcelize
data class Daily(
    val dt:Long,
    val sunrise:Long,
    val sunset:Long,
    val temp: Temp,
    val feels_like: FeelsLike,
    val pressure:Float,
    val humidity: Float,
    val dew_point: Double,
    val wind_speed:Float,
    val wind_deg:Float,
    val clouds: Float,
    val pop:Float,
    val uvi: Float
) : Parcelable

@Parcelize
data class Temp(
    val day:Double,
    val min:Double,
    val max:Double,
    val night:Double,
    val eve:Double,
    val morn:Double
) : Parcelable

@Parcelize
data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
) : Parcelable



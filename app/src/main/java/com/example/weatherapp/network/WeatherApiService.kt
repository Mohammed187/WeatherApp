package com.example.weatherapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("weather?")
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units:String,
        @Query("APPID") app_id: String
    ) : WeatherModel

    @GET("onecall?")
    suspend fun getDailyWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude:String,
        @Query("units") units:String,
        @Query("APPID") app_id: String
    ) : DailyWeatherModel
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

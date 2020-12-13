package com.example.weatherapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.daily.DailyItemsAdapter
import com.example.weatherapp.daily.WeatherApiStatus
import com.example.weatherapp.network.Daily
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("formatTime")
fun formatTime(textView: TextView, dt: Long) {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    textView.text = sdf.format(getDateFromTimeStamp(dt))
}

@BindingAdapter("temperatureFormatted")
fun setTemperatureFormatted(textView: TextView, temp: Double) {
    val tempAsString = temp.toString().substringBefore(".") + "°C"
    textView.text = tempAsString
}

@BindingAdapter("dateShortFormatted")
fun setDateShortFormatted(textView: TextView, dt: Long) {
    val sdf = SimpleDateFormat("EEE, d MMM yyyy hh:mm a", Locale.getDefault())
    textView.text = sdf.format(getDateFromTimeStamp(dt))
}

private fun getDateFromTimeStamp(time: Long): Date {
    val mili = 1000L
    val timestamp = Timestamp(time * mili)
    return Date(timestamp.time)
}

@BindingAdapter("listData")
fun bindRecyclerview(recyclerView: RecyclerView, data: List<Daily>?) {
    val adapter = recyclerView.adapter as DailyItemsAdapter
    adapter.submitList(data)
}

@BindingAdapter("weatherApiStatus")
fun bindStatus(statusImageView: ImageView, status: WeatherApiStatus?) {
    when (status) {
        WeatherApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_forklift)
        }
        WeatherApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        WeatherApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("humidityFormatted")
fun setHumidityFormatted(textView: TextView, humidity: Double) {
    val humidityAsString = humidity.toString().substringBefore(".") + "%"
    textView.text = humidityAsString
}

@BindingAdapter("pressureFormatted")
fun setPressureFormatted(textView: TextView, pressure: Double) {
    val pressureAsString = pressure.toString().substringBefore(".") + " hPa"
    textView.text = pressureAsString
}

@BindingAdapter("windSpeedFormatted")
fun setWindSpeed(textView: TextView, wind: Double) {
    val windAsString = wind.toString().substringBefore(".") + " m/s"
    textView.text = windAsString
}
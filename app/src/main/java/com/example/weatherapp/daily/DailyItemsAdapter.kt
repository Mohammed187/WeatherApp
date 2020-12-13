package com.example.weatherapp.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.DailyWeatherItemBinding
import com.example.weatherapp.network.Daily

class DailyItemsAdapter: ListAdapter<Daily, DailyItemsAdapter.WeatherViewHolder>(DiffCallback) {

    class WeatherViewHolder(private var binding:DailyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherModel: Daily?) {
            binding.widget = weatherModel

            binding.executePendingBindings()
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = DailyWeatherItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherModel = getItem(position)
        holder.bind(weatherModel)
    }

}
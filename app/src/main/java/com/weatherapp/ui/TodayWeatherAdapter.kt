package com.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.data.remote.model.WeatherData
import com.weatherapp.databinding.TodayWeatherItemBinding

class TodayWeatherAdapter : ListAdapter<WeatherData, TodayWeatherAdapter.MyViewHolder>(DiffCallback()) {

    class MyViewHolder(private val binding: TodayWeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherData) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TodayWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return (oldItem.dt == newItem.dt && oldItem.main.temp == newItem.main.temp)
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

}
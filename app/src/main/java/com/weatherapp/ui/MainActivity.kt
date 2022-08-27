package com.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R
import com.weatherapp.databinding.ActivityMainBinding
import com.weatherapp.utils.Resource
import com.weatherapp.utils.toCelsius
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter = TodayWeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.todayRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.todayRecyclerView.adapter = adapter

        viewModel.result.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.cityText.setText(it.currentWeather?.name)
                    binding.tempText.setText(it.currentWeather?.main?.temp.toCelsius())
                    binding.weatherConditionsText.setText(it.currentWeather?.weather?.get(0)?.description)
                    binding.tempRangeText.setText("H:${it.currentWeather?.main?.temp_max.toCelsius()}  L:${it.currentWeather?.main?.temp_min.toCelsius()}")
                    adapter.submitList(it.data?.list)
                }
                is Resource.Error -> Toast.makeText(this, it.message ?: "", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
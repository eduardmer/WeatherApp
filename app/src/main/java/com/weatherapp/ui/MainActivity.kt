package com.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R
import com.weatherapp.databinding.ActivityMainBinding
import com.weatherapp.utils.toCelsius
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val weatherAdapter = TodayWeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val cityAdapter = CityAdapter(this, R.layout.city_item, mutableListOf())
        binding.cities.apply {
            setAdapter(cityAdapter)
            setOnItemClickListener{_, _, position, _ ->
                viewModel.updateSelectedCity(cityAdapter.getItem(position))
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = weatherAdapter
        }

        viewModel.allCities.observe(this) {
            cityAdapter.updateData(it)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weather.collect {
                    when (it) {
                        is WeatherScreenUiState.Success -> {
                            binding.tempRangeText.text =
                                "H:${it.currentWeather.main.temp_max.toCelsius()}  L:${it.currentWeather.main.temp_min.toCelsius()}"
                            binding.data = it.currentWeather
                            weatherAdapter.submitList(it.todayWeather.list)
                            showProgressBar(false)
                        }
                        is WeatherScreenUiState.Error -> {
                            Toast.makeText(applicationContext, it.message ?: "", Toast.LENGTH_SHORT).show()
                            showProgressBar(false)
                        }
                        is WeatherScreenUiState.Loading -> showProgressBar(true)
                    }
                }
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

}
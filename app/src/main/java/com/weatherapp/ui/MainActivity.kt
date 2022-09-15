package com.weatherapp.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val weatherAdapter = TodayWeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.findLocation.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ->
                    viewModel.getCurrentLocation()
                else -> ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

        binding.todayRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = weatherAdapter
        }

        viewModel.result.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.cityText.setText(it.currentWeather?.name)
                    binding.tempText.setText(it.currentWeather?.main?.temp.toCelsius())
                    binding.weatherConditionsText.setText(it.currentWeather?.weather?.get(0)?.description)
                    binding.tempRangeText.setText("H:${it.currentWeather?.main?.temp_max.toCelsius()}  L:${it.currentWeather?.main?.temp_min.toCelsius()}")
                    binding.data = it.currentWeather
                    weatherAdapter.submitList(it.data?.list)
                }
                is Resource.Error -> Toast.makeText(this, it.message ?: "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    viewModel.getCurrentLocation()
                else
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
package com.weatherapp.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R
import com.weatherapp.data.local.City
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

        val cities = ArrayList<City>()
        cities.add(City(1, "Tirane",19.819025,41.327953))
        cities.add(City(2, "Durres", 19.456469, 41.324590))
        cities.add(City(3, "Kavaje", 19.562760, 41.184453))

        val cityAdapter = CityAdapter(this, R.layout.city_item, cities)
        binding.cities.apply {
            setAdapter(cityAdapter)
            setOnItemClickListener{adapter, view, position, id ->
                val selected = cityAdapter.getItem(position)
                if (selected != null)
                    viewModel.updateSelectedCity(selected)
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
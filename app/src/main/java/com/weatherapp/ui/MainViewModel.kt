package com.weatherapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.CityPreferences
import com.weatherapp.data.Repository
import com.weatherapp.data.local.City
import com.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _result = MutableLiveData<Resource>()
    val result: LiveData<Resource> = _result

    init {
        getWeath()
    }

    private fun getWeath() {
        viewModelScope.launch {
            repository.weather.collect {
                _result.value = it
            }
        }
    }

    fun updateSelectedCity(city: City) {
        viewModelScope.launch {
            repository.updateSelectedCity(city)
        }
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                val currentWeather = repository.getCurrentWeather(appid = "37f6ca3eb5a94ec5ff7d9600bef088c8")
                val response = repository.getWeather(appid = "37f6ca3eb5a94ec5ff7d9600bef088c8")
                _result.value = Resource.Success(currentWeather, response)
            } catch (ex : Exception) {
                _result.value = Resource.Error(ex.message)
            }
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            val location = repository.getCurrentLocation()
            Log.i("location", "${location?.latitude} ${location?.longitude}")
        }
    }

}
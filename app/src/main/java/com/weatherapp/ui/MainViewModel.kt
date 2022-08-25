package com.weatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.weatherapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            val response = repository.getWeather(appid = "37f6ca3eb5a94ec5ff7d9600bef088c8")
            Log.i("pergjigja", Gson().toJson(response))
        }
    }

}
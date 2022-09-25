package com.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.Repository
import com.weatherapp.data.local.City
import com.weatherapp.data.remote.dto.CurrentWeatherResponse
import com.weatherapp.data.remote.dto.TodayWeatherResponse
import com.weatherapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _result = MutableLiveData<WeatherScreenUiState>()
    val result: LiveData<WeatherScreenUiState> = _result
    val allCities = MutableLiveData<List<City>>()

    init {
        getWeath()
        getAllCities()
    }

    private fun getWeath() {
        viewModelScope.launch {
            repository.weather.collect {
                when(it) {
                    is Result.Success -> _result.value = WeatherScreenUiState.Success(it.currentWeather, it.todayWeather)
                    is Result.Error -> _result.value = WeatherScreenUiState.Error(it.message)
                    is Result.Loading -> _result.value = WeatherScreenUiState.Loading
                }
            }
        }
    }

    private fun getAllCities() {
        viewModelScope.launch {
            allCities.value = repository.getAllCities()
        }
    }

    fun updateSelectedCity(city: City) {
        viewModelScope.launch {
            repository.updateSelectedCity(city)
        }
    }

}

sealed interface WeatherScreenUiState {
    data class Success(val currentWeather: CurrentWeatherResponse, val todayWeather: TodayWeatherResponse) : WeatherScreenUiState
    data class Error(val message: String?) : WeatherScreenUiState
    object Loading : WeatherScreenUiState
}

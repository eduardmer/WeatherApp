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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _allCities = MutableLiveData<List<City>>()
    val allCities: LiveData<List<City>> = _allCities

    init {
        getAllCities()
    }

    val weather: StateFlow<WeatherScreenUiState> = repository.weather.map {
        when(it) {
            is Result.Success -> WeatherScreenUiState.Success(it.currentWeather, it.todayWeather)
            is Result.Error -> WeatherScreenUiState.Error(it.message)
            Result.Loading -> WeatherScreenUiState.Loading
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        WeatherScreenUiState.Loading
    )

    private fun getAllCities() {
        viewModelScope.launch {
            _allCities.value = repository.getAllCities()
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

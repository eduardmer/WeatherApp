package com.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.Repository
import com.weatherapp.data.local.model.CityDto
import com.weatherapp.data.remote.model.CurrentWeatherResponse
import com.weatherapp.data.remote.model.TodayWeatherResponse
import com.weatherapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _allCities = MutableLiveData<List<CityDto>>()
    val allCities: LiveData<List<CityDto>> = _allCities

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

    fun updateSelectedCity(city: CityDto) {
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

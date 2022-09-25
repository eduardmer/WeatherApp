package com.weatherapp.utils

import com.weatherapp.data.remote.dto.CurrentWeatherResponse
import com.weatherapp.data.remote.dto.TodayWeatherResponse

sealed interface Result {
    data class Success(val currentWeather: CurrentWeatherResponse, val todayWeather: TodayWeatherResponse) : Result
    data class Error(val message: String?) : Result
    object Loading : Result
}

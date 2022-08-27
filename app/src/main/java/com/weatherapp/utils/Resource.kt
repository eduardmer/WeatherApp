package com.weatherapp.utils

import com.weatherapp.data.remote.dto.CurrentWeatherResponse
import com.weatherapp.data.remote.dto.TodayWeatherResponse

sealed class Resource(val currentWeather: CurrentWeatherResponse? = null, val data: TodayWeatherResponse? = null, val message: String? = null) {
    class Success(currentWeather: CurrentWeatherResponse, data: TodayWeatherResponse) : Resource(currentWeather, data)
    class Error(message: String?) : Resource(message = message)
}

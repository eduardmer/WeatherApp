package com.weatherapp.data.remote.dto

data class CurrentWeatherResponse(val code: Int, val message: String, val name: String, val weather: List<Weather>, val main: Main)

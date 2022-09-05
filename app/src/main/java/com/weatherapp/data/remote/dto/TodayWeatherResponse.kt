package com.weatherapp.data.remote.dto

data class TodayWeatherResponse(val cod: String,
                                val message: String,
                                val list: List<WeatherData>,
                                val city: City)

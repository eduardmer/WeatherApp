package com.weatherapp.data.remote.model

data class WeatherData(val dt: Long, val main: Main, val weather: List<Weather>, val wind: Wind, val pop: Double)

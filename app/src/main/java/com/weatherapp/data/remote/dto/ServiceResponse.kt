package com.weatherapp.data.remote.dto

data class ServiceResponse(val cod: String, val message: String, val list: List<WeatherData>)

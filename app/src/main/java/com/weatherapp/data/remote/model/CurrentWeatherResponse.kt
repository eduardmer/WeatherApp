package com.weatherapp.data.remote.model

data class CurrentWeatherResponse(val code: Int,
                                  val message: String,
                                  val name: String,
                                  val weather: List<Weather>,
                                  val main: Main,
                                  val wind: Wind,
                                  val sys: Sys)

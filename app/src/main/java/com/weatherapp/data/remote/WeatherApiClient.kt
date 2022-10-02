package com.weatherapp.data.remote

import javax.inject.Inject

class WeatherApiClient @Inject constructor(private val service: WeatherService) {

    suspend fun getCurrentWeather(lat: Double, lng: Double, appId: String) =
        service.getCurrentWeather(lat, lng, appId)

    suspend fun getTodayWeather(lat: Double, lng: Double, appId: String) =
        service.getTodayWeather(lat, lng, appId)

}